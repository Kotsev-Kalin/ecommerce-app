# AI safety

- The LangGraph service has no database credentials and only calls allow-listed Spring Boot HTTP tools.
- Spring Boot remains responsible for JWT validation, ownership, roles, validation, and all business mutations.
- Action-like language routes to `approval_gate`, which uses a LangGraph interrupt and accepts only `approved`, `rejected`, or `revise` decisions on resume.
- The current approval demonstrator deliberately records the decision without performing a mutation; an approved action needs a separately secured backend endpoint.
- `session_id` is used as the checkpoint thread ID and must be scoped to one authenticated user in a production deployment.
- Do not log bearer tokens, passwords, payment data, or unredacted personal data in workflow traces.
- Demo recommendations are restricted to active, in-stock catalog products and return a no-match response instead of inventing inventory.
- Price and source references come directly from the product repository.
- `AI_API_KEY` is server-only and the optional OpenAI-compatible provider is disabled by default.
- Enrichment is admin-only and suggestion-only; it never persists product data.
- Validate prompt length, show server errors to users, and route policy, payment, medical, legal, and safety-critical questions to verified support channels.
