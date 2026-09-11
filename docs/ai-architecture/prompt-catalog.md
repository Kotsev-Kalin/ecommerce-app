# Prompt catalog

| Capability | Contract |
| --- | --- |
| Demo shopping assistant | Responds only with active, in-stock references retrieved from the product catalog. |
| OpenAI-compatible assistant | Sends the question and retrieved catalog fields with a system instruction forbidding invented prices, availability, policies, and attributes. |
| Product enrichment | Deterministically normalizes only admin-supplied title, description, category, and image URL into review-only suggestions. |

Provider requests never include credentials, payment data, order history, or customer profiles.
