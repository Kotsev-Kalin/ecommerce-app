# AI safety

- Demo recommendations are restricted to active, in-stock catalog products and return a no-match response instead of inventing inventory.
- Price and source references come directly from the product repository.
- `AI_API_KEY` is server-only and the optional OpenAI-compatible provider is disabled by default.
- Enrichment is admin-only and suggestion-only; it never persists product data.
- Validate prompt length, show server errors to users, and route policy, payment, medical, legal, and safety-critical questions to verified support channels.
