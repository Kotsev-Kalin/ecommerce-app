package com.ecommerce.ai.search;

import com.ecommerce.ai.dto.AiProductReference;
import java.util.List;

/** Replace this fallback with an embeddings/pgvector implementation without changing AI APIs. */
public interface ProductSearchService {
    List<AiProductReference> search(String query, int limit);
}
