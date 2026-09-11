# Grounded shopping assistant architecture

`POST /api/ai/assistant` validates a customer question, retrieves catalog data through `ProductSearchService`, and provides only retrieved references to `AiProvider`. The response includes the generated answer, recommendations, and the exact catalog sources.

`KeywordProductSearchService` is the JPA/PostgreSQL-compatible default: it term-matches active, in-stock product names, descriptions, and categories. A future `PgVectorProductSearchService` can implement the same interface, store embeddings in pgvector, and retain keyword search as its fallback. No pgvector dependency is required locally.
