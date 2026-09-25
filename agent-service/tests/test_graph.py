from unittest.mock import patch
from uuid import uuid4

from langchain_core.messages import AIMessage, HumanMessage, ToolMessage
from langgraph.types import Command

from app.graph import workflow


class FakeHttpResponse:
    def __init__(self, payload):
        self.payload = payload

    def raise_for_status(self):
        return None

    def json(self):
        return self.payload


class ScriptedToolCallingModel:
    """Offline model double that follows the same bind_tools/tool-call protocol."""

    def __init__(self, **_kwargs):
        self.tool_names = set()

    def bind_tools(self, tools):
        self.tool_names = {tool.name for tool in tools}
        return self

    def invoke(self, messages):
        last_message = messages[-1]
        if isinstance(last_message, HumanMessage):
            request = last_message.content.lower()
            if "order" in request or "delivery" in request:
                return AIMessage(
                    content="",
                    tool_calls=[{"name": "get_my_orders", "args": {}, "id": "orders-call"}],
                )
            if "product 1" in request:
                return AIMessage(
                    content="",
                    tool_calls=[{"name": "get_product", "args": {"product_id": 1}, "id": "detail-call"}],
                )
            return AIMessage(
                content="",
                tool_calls=[{"name": "search_products", "args": {"query": last_message.content}, "id": "search-call"}],
            )
        if isinstance(last_message, ToolMessage):
            if last_message.name == "get_my_orders":
                return AIMessage(content="Your order #42 is being prepared.")
            if last_message.name == "get_product":
                return AIMessage(content="Product 1 is the Atlas Laptop and it is in stock.")
            return AIMessage(content="The Atlas Laptop costs 1200.00 and is in stock.")
        raise AssertionError(f"Unexpected message: {last_message!r}")


def mock_backend_get(url, **_kwargs):
    if url.endswith("/products/1"):
        return FakeHttpResponse({"id": 1, "name": "Atlas Laptop", "price": 1200.00, "stockQuantity": 3})
    if url.endswith("/products"):
        return FakeHttpResponse([{"id": 1, "name": "Atlas Laptop", "price": 1200.00, "stockQuantity": 3}])
    if url.endswith("/orders"):
        return FakeHttpResponse([{"id": 42, "status": "PREPARING"}])
    raise AssertionError(f"Unexpected URL: {url}")


def invoke_request(request, token=None):
    config = {"configurable": {"thread_id": str(uuid4())}}
    state = workflow.invoke(
        {"user_request": request, "user_token": token, "messages": [HumanMessage(content=request)]},
        config,
    )
    return state, config


@patch("app.tools.httpx.get", side_effect=mock_backend_get)
@patch("app.graph.ChatOpenAI", ScriptedToolCallingModel)
def test_catalog_request_uses_search_tool(_mock_get):
    state, _config = invoke_request("Recommend a laptop under 1500")

    assert state["intent"] == "catalog"
    assert state["final_answer"] == "The Atlas Laptop costs 1200.00 and is in stock."


@patch("app.tools.httpx.get", side_effect=mock_backend_get)
@patch("app.graph.ChatOpenAI", ScriptedToolCallingModel)
def test_product_detail_request_uses_detail_tool(_mock_get):
    state, _config = invoke_request("Show product 1 details")

    assert state["intent"] == "catalog"
    assert state["final_answer"] == "Product 1 is the Atlas Laptop and it is in stock."


@patch("app.tools.httpx.get", side_effect=mock_backend_get)
@patch("app.graph.ChatOpenAI", ScriptedToolCallingModel)
def test_order_request_uses_authenticated_order_tool(_mock_get):
    state, _config = invoke_request("Where is my order?", token="test-jwt")

    assert state["intent"] == "order"
    assert state["final_answer"] == "Your order #42 is being prepared."


def test_cancel_request_requires_and_honors_rejection():
    state, config = invoke_request("Cancel my order")
    assert "__interrupt__" in state

    resumed = workflow.invoke(Command(resume={"decision": "rejected", "feedback": None}), config)
    assert resumed["final_answer"] == "No action was taken because approval was not granted."


def test_address_change_can_be_revised_after_interrupt():
    state, config = invoke_request("Change my delivery address")
    assert "__interrupt__" in state

    resumed = workflow.invoke(
        Command(resume={"decision": "revise", "feedback": "Use my work address."}),
        config,
    )
    assert resumed["final_answer"] == "No action was taken. Feedback recorded: Use my work address."