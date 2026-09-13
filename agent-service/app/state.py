from typing import Annotated, Literal, TypedDict

from langgraph.graph.message import add_messages


class WorkflowState(TypedDict, total=False):
    """Shared, checkpointed state passed between LangGraph nodes."""

    messages: Annotated[list, add_messages]
    user_request: str
    user_token: str | None
    intent: Literal["catalog", "order", "action", "unsupported"]
    products: list[dict]
    orders: list[dict]
    draft_answer: str
    final_answer: str
    approval_request: dict
    human_decision: Literal["approved", "rejected", "revise"] | None
    human_feedback: str | None
