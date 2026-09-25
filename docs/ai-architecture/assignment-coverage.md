# Presentation Requirements Coverage

This document maps the implemented e-commerce workflow to the course topics: LangChain tools and agents, LangGraph stateful workflows, memory, human-in-the-loop control, and multi-agent coordination.

| Topic | Implemented evidence | Assessment demonstration |
| --- | --- | --- |
| LangChain tools | `agent-service/app/tools.py` defines `@tool` functions for catalog search, product lookup, and authenticated order lookup. | Explain that tools return structured data from Spring Boot rather than relying on model knowledge. |
| Agent roles | `agent-service/app/prompts.py` separates router, catalog, order-support, and approval responsibilities; catalog and order specialists invoke `ChatOpenAI.bind_tools(...)`. | Show that each specialist receives its system prompt and only its least-privilege tools. |
| LangGraph state | `agent-service/app/state.py` defines `WorkflowState(TypedDict)`. | Describe how request, intent, products, orders, draft answer, and approval decision move between nodes. |
| Directed graph | `agent-service/app/graph.py` compiles a `StateGraph` with conditional routing and explicit `START`/`END` edges. | Draw the graph and follow one catalog request and one order request. |
| Multi-agent pattern | Router delegates to the Catalog Agent or Order Support Agent; the finalizer produces the response. | Explain this as a small supervisor/router design, not uncontrolled autonomous delegation. |
| Memory and checkpoints | `MemorySaver` is attached when the graph is compiled and `session_id` becomes the LangGraph `thread_id`. | Start an action request, show the returned `awaiting_approval` state, then resume the same session. |
| Human in the loop | `approval_gate` uses LangGraph `interrupt`; `/workflow/{sessionId}/resume` continues with approved, rejected, or revise decisions. | Demonstrate both approval and revision/rejection paths. |
| Guardrails | Route classification, typed tools, bounded input fields, Spring authorization, and a non-mutating action demonstrator are implemented. | Explain that policy and authorization are enforced in code and Spring Security, not merely requested in prompts. |
| Grounding/RAG approach | Existing `ProductSearchService` and LangChain catalog tools fetch catalog records; answer output includes retrieved record fields. | State that this is retrieval grounding over the live product catalog, with keyword retrieval as the current baseline. |
| Evaluation | `agent-service/tests/test_graph.py` executes five offline workflow cases with mocked HTTP tools and a scripted tool-calling model. | Run `pytest tests/test_graph.py -q`; the Colab notebook also shows five interactive scenarios. |

## Required Demonstration Cases

1. `Recommend a laptop under 1500` routes to the Catalog Agent and returns only catalog products.
2. `Show product 1 details` demonstrates a typed catalog-detail tool when added to a specialist path.
3. `Where is my order?` routes to Order Support and requires a valid JWT token.
4. `Cancel my order` interrupts for a human decision; resume with `rejected` and show that no mutation occurs.
5. `Change my delivery address` interrupts; resume with `revise` and feedback to show controlled revision.

## Honest Scope Statement

The application is a production-style Java/Angular e-commerce product extended with a Python LangChain/LangGraph demonstrator. Its Catalog and Order Support LLM specialists use only read-only data tools, while the graph routes potentially consequential requests to a human approval interrupt. The service does not allow an LLM to execute payments, refunds, or order changes; those remain secured Spring Boot responsibilities and require a separately implemented business endpoint after approval.
