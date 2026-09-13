# AI Agents and Runtime Roles

## Runtime Agent Workflow

The e-commerce application includes a Python LangChain/LangGraph agent service in `agent-service/`. It is a bounded supervisor/router workflow, not an unrestricted autonomous system.

| Runtime role | File | Responsibility | Tools or authority |
| --- | --- | --- | --- |
| Router | `app/graph.py:route_request` | Classifies catalog, order, action, or unsupported requests. | Routing only; no data or mutation authority. |
| Catalog Agent | `app/graph.py:catalog_agent` | Finds active catalog products and presents retrieved fields. | `search_products`, read-only. |
| Order Support Agent | `app/graph.py:order_support_agent` | Summarizes the authenticated user's orders. | `get_my_orders`, read-only and JWT-backed. |
| Approval Gate | `app/graph.py:approval_gate` | Pauses action-like requests until a human approves, rejects, or requests revision. | LangGraph `interrupt`; no mutation authority. |
| Finalizer | `app/graph.py:finalize` | Produces a bounded final response from state. | No tools. |

`WorkflowState` is checkpointed by `MemorySaver` using a session-specific LangGraph `thread_id`. The graph preserves state across an interrupt and a resume request.

## Development Assistance

### GitHub Copilot (Inline + Chat)

GitHub Copilot was the main implementation assistant used during daily coding. It helped generate code inside the editor, suggested repetitive patterns, and accelerated CRUD-heavy files across both the Spring Boot backend and the Angular frontend.

### Capabilities used

- Inline code completion for entities, DTOs, services, and templates
- Chat-based generation of controllers, guards, interceptors, and validation logic
- Repetitive boilerplate generation for repository interfaces and Angular HTTP services
- Quick test scaffolding and implementation hints while refactoring

### What it helped build

- JPA entities and repository interfaces
- JWT auth DTOs and service methods
- Angular auth/product/cart/order services
- Login/register/cart/checkout templates
- Admin product management pages

### Architecture and Review Assistance

Claude Code was used as the architecture and review assistant. It was most valuable when thinking across multiple layers at the same time: package structure, security flow, endpoint consistency, missing support classes, and documentation for the AI-assisted assignment.

### Capabilities used

- High-level system design
- Cross-file consistency review
- Security architecture review for JWT and role-based access
- Service-layer workflow planning for cart and checkout
- Documentation synthesis and evidence writing

### What it helped build

- Backend package structure and missing support files
- Security class list and filter-chain setup
- Checkout/order processing sequence
- Documentation set under `docs/ai-architecture`

### Development Collaboration Pattern

The workflow was intentionally split:

1. **Claude Code planned the shape of the feature** — architecture, dependencies, and missing pieces.
2. **GitHub Copilot generated the implementation details** — entities, methods, DTOs, templates, and repetitive wiring.
3. **Claude Code reviewed the assembled feature** — especially for consistency between backend endpoints and frontend API calls.
4. **GitHub Copilot polished local edits** — imports, validation, small refactors, and test skeletons.
5. **Claude Code helped package the evidence** — prompts, hooks, skills, and development narrative.

The development-assistance record is separate from the runtime LangGraph agents above: development tools helped create and review code, while runtime agents serve the application workflow.
