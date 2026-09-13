from uuid import uuid4

from fastapi import FastAPI, Header, HTTPException
from pydantic import BaseModel, ConfigDict, Field
from langchain_core.messages import HumanMessage

from .graph import resume_workflow, workflow

app = FastAPI(title="E-Commerce LangGraph Agent Service", version="1.0.0")


class WorkflowRequest(BaseModel):
    model_config = ConfigDict(extra="forbid")

    request: str = Field(min_length=1, max_length=500)


class ResumeRequest(BaseModel):
    decision: str = Field(pattern="^(approved|rejected|revise)$")
    feedback: str | None = Field(default=None, max_length=500)


def response_payload(state: dict, session_id: str) -> dict:
    return {
        "sessionId": session_id,
        "status": "awaiting_approval" if "__interrupt__" in state else "completed",
        "answer": state.get("final_answer") or state.get("draft_answer"),
        "intent": state.get("intent"),
        "products": state.get("products", []),
        "orders": state.get("orders", []),
        "approval": state.get("approval_request"),
    }


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}


@app.post("/workflow")
def run_workflow(request: WorkflowRequest, authorization: str | None = Header(default=None)) -> dict:
    session_id = str(uuid4())
    token = authorization.removeprefix("Bearer ") if authorization else None
    config = {"configurable": {"thread_id": session_id}}
    state = workflow.invoke(
        {"user_request": request.request, "user_token": token, "messages": [HumanMessage(content=request.request)]},
        config,
    )
    return response_payload(state, session_id)


@app.post("/workflow/{session_id}/resume")
def resume(session_id: str, request: ResumeRequest) -> dict:
    try:
        state = resume_workflow(session_id, request.decision, request.feedback)
    except Exception as exception:
        raise HTTPException(status_code=409, detail="No resumable approval exists for this session.") from exception
    return response_payload(state, session_id)
