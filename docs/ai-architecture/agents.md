# AI Agents Used in Development

## Primary Agent: GitHub Copilot (Inline + Chat)

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

## Secondary Agent: Claude Code (Claude Sonnet)

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

## Agent Collaboration Pattern

The workflow was intentionally split:

1. **Claude Code planned the shape of the feature** — architecture, dependencies, and missing pieces.
2. **GitHub Copilot generated the implementation details** — entities, methods, DTOs, templates, and repetitive wiring.
3. **Claude Code reviewed the assembled feature** — especially for consistency between backend endpoints and frontend API calls.
4. **GitHub Copilot polished local edits** — imports, validation, small refactors, and test skeletons.
5. **Claude Code helped package the evidence** — prompts, hooks, skills, and development narrative.

In other words, Copilot acted like a fast pair programmer inside files, while Claude Code acted like an architecture reviewer and project-level assistant.
