import os
from uuid import uuid4

from langchain_core.messages import AIMessage, HumanMessage, SystemMessage
from langchain_openai import ChatOpenAI
from langgraph.checkpoint.memory import MemorySaver
from langgraph.graph import END, START, StateGraph
from langgraph.prebuilt import ToolNode
from langgraph.types import Command, interrupt

from .prompts import APPROVAL_SYSTEM_PROMPT, CATALOG_SYSTEM_PROMPT, ORDER_SYSTEM_PROMPT
from .state import WorkflowState
from .tools import get_my_orders, get_product, search_products


def route_request(state: WorkflowState) -> dict:
    request = state["user_request"].lower()
    if any(word in request for word in ("cancel", "refund", "change address", "checkout", "place order")) or (
        "change" in request and "address" in request
    ):
        intent = "action"
    elif any(word in request for word in ("order", "delivery", "shipping", "track")):
        intent = "order"
    elif any(word in request for word in ("product", "recommend", "laptop", "phone", "price", "catalog")):
        intent = "catalog"
    else:
        intent = "unsupported"
    return {"intent": intent}


def _model_with_tools(tools: list):
    """Create the provider-backed model only when a specialist is invoked."""
    return ChatOpenAI(
        model=os.getenv("AGENT_MODEL", "gpt-4o-mini"),
        temperature=0,
    ).bind_tools(tools)


def catalog_agent(state: WorkflowState) -> dict:
    response = _model_with_tools([search_products, get_product]).invoke(
        [SystemMessage(content=CATALOG_SYSTEM_PROMPT), *state["messages"]]
    )
    return {"messages": [response]}


def order_support_agent(state: WorkflowState) -> dict:
    if not state.get("user_token"):
        answer = "Sign in before requesting your order information."
        return {"draft_answer": answer, "messages": [AIMessage(content=answer, name="order_support_agent")]}
    response = _model_with_tools([get_my_orders]).invoke(
        [SystemMessage(content=ORDER_SYSTEM_PROMPT), *state["messages"]]
    )
    return {"messages": [response]}


def approval_gate(state: WorkflowState) -> dict:
    proposal = {
        "message": "This request may change an order or checkout state. Approve, reject, or request a revision.",
        "policy": APPROVAL_SYSTEM_PROMPT,
        "request": state["user_request"],
    }
    decision = interrupt(proposal)
    choice = decision.get("decision", "rejected") if isinstance(decision, dict) else "rejected"
    feedback = decision.get("feedback") if isinstance(decision, dict) else None
    return {"approval_request": proposal, "human_decision": choice, "human_feedback": feedback}


def finalize(state: WorkflowState) -> dict:
    if state["intent"] == "unsupported":
        answer = "I can help with catalog discovery, product recommendations, and your order status."
    elif state["intent"] == "action":
        if state.get("human_decision") == "approved":
            answer = "Approval recorded. The Spring Boot API must independently authorize and execute the requested action."
        elif state.get("human_decision") == "revise":
            answer = f"No action was taken. Feedback recorded: {state.get('human_feedback') or 'Please revise the request.'}"
        else:
            answer = "No action was taken because approval was not granted."
    else:
        answer = state.get("draft_answer") or state["messages"][-1].content
    return {"final_answer": answer, "messages": [AIMessage(content=answer, name="finalizer")]}


def route_after_router(state: WorkflowState) -> str:
    return {"catalog": "catalog_agent", "order": "order_support_agent", "action": "approval_gate"}.get(state["intent"], "finalize")


def route_after_agent(state: WorkflowState) -> str:
    last_message = state["messages"][-1]
    return "tools" if getattr(last_message, "tool_calls", None) else "finalize"


def build_graph():
    builder = StateGraph(WorkflowState)
    builder.add_node("router", route_request)
    builder.add_node("catalog_agent", catalog_agent)
    builder.add_node("catalog_tools", ToolNode([search_products, get_product]))
    builder.add_node("order_support_agent", order_support_agent)
    builder.add_node("order_tools", ToolNode([get_my_orders]))
    builder.add_node("approval_gate", approval_gate)
    builder.add_node("finalize", finalize)
    builder.add_edge(START, "router")
    builder.add_conditional_edges("router", route_after_router)
    builder.add_conditional_edges("catalog_agent", route_after_agent, {"tools": "catalog_tools", "finalize": "finalize"})
    builder.add_edge("catalog_tools", "catalog_agent")
    builder.add_conditional_edges("order_support_agent", route_after_agent, {"tools": "order_tools", "finalize": "finalize"})
    builder.add_edge("order_tools", "order_support_agent")
    builder.add_edge("approval_gate", "finalize")
    builder.add_edge("finalize", END)
    return builder.compile(checkpointer=MemorySaver())


workflow = build_graph()


def execute_workflow(user_request: str) -> dict:
    """Assignment entry point for a new, read-only customer workflow."""
    config = {"configurable": {"thread_id": str(uuid4())}}
    return workflow.invoke({"user_request": user_request, "messages": [HumanMessage(content=user_request)]}, config)


def resume_workflow(thread_id: str, decision: str, feedback: str | None = None) -> dict:
    config = {"configurable": {"thread_id": thread_id}}
    return workflow.invoke(Command(resume={"decision": decision, "feedback": feedback}), config)
