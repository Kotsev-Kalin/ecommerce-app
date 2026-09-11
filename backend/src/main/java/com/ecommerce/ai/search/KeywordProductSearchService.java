package com.ecommerce.ai.search;

import com.ecommerce.ai.dto.AiProductReference;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeywordProductSearchService implements ProductSearchService {
    private static final Set<String> STOP_WORDS = Set.of("a", "an", "and", "for", "i", "is", "me", "need", "of", "the", "to", "with");
    private final ProductRepository productRepository;
    public KeywordProductSearchService(ProductRepository productRepository) { this.productRepository = productRepository; }

    @Override @Transactional(readOnly = true)
    public List<AiProductReference> search(String query, int limit) {
        List<String> terms = Arrays.stream(query.toLowerCase(Locale.ROOT).split("[^\\p{Alnum}]+"))
                .filter(term -> term.length() > 1 && !STOP_WORDS.contains(term)).distinct().toList();
        return productRepository.findAll().stream()
                .filter(product -> product.isActive() && product.getStockQuantity() != null && product.getStockQuantity() > 0)
                .map(product -> new ScoredProduct(product, score(product, terms)))
                .filter(scored -> terms.isEmpty() || scored.score() > 0)
                .sorted(Comparator.comparingInt(ScoredProduct::score).reversed().thenComparing(scored -> scored.product().getName()))
                .limit(Math.max(1, Math.min(limit, 10))).map(scored -> reference(scored.product())).toList();
    }
    private int score(Product product, List<String> terms) {
        String text = (product.getName() + " " + safe(product.getDescription()) + " " + (product.getCategory() == null ? "" : product.getCategory().getName())).toLowerCase(Locale.ROOT);
        return (int) terms.stream().filter(text::contains).count();
    }
    private AiProductReference reference(Product product) {
        return AiProductReference.builder().productId(product.getId()).name(product.getName())
                .categoryName(product.getCategory() == null ? null : product.getCategory().getName()).price(product.getPrice())
                .imageUrl(product.getImageUrl()).stockQuantity(product.getStockQuantity()).source("catalog").build();
    }
    private String safe(String value) { return value == null ? "" : value; }
    private record ScoredProduct(Product product, int score) {}
}
