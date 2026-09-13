# Prompt catalog

## Runtime LangGraph Prompts

The executable prompt constants live in `agent-service/app/prompts.py`.

| Role | Prompt policy |
| --- | --- |
| Router | Classifies requests but never authorizes actions. |
| Catalog Agent | Uses only retrieved product records and cites IDs, prices, and stock exactly as returned. |
| Order Support Agent | Uses only authenticated order records and never changes an order. |
| Approval Gate | Requires explicit human approval before a request that could change order, payment, shipping, or checkout state. |

## Spring Assistant Prompts

| Capability | Contract |
| --- | --- |
| Demo shopping assistant | Responds only with active, in-stock references retrieved from the product catalog. |
| OpenAI-compatible assistant | Sends the question and retrieved catalog fields with a system instruction forbidding invented prices, availability, policies, and attributes. |
| Product enrichment | Deterministically normalizes only admin-supplied title, description, category, and image URL into review-only suggestions. |

Provider requests never include credentials, payment data, order history, or customer profiles.
