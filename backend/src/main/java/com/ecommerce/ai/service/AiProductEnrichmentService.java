package com.ecommerce.ai.service;

import com.ecommerce.ai.dto.AiEnrichmentRequest;
import com.ecommerce.ai.dto.AiEnrichmentResponse;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class AiProductEnrichmentService {
    public AiEnrichmentResponse suggest(AiEnrichmentRequest request) {
        String title = title(request.getTitle());
        String category = request.getCategory() == null || request.getCategory().isBlank() ? "Uncategorized" : title(request.getCategory());
        LinkedHashSet<String> tags = new LinkedHashSet<>(); tags.add(category.toLowerCase(Locale.ROOT));
        Arrays.stream(title.toLowerCase(Locale.ROOT).split("[^\\p{Alnum}]+")).filter(word -> word.length() > 2).limit(5).forEach(tags::add);
        return AiEnrichmentResponse.builder().suggestedTitle(title)
                .suggestedDescription(request.getDescription() == null || request.getDescription().isBlank() ? "Discover " + title + ". Review and customize this catalog description before publishing." : request.getDescription().trim())
                .suggestedCategory(category).suggestedTags(List.copyOf(tags))
                .suggestedAltText(request.getImageUrl() == null || request.getImageUrl().isBlank() ? title + " product image" : title + " product image for the " + category + " category")
                .notice("Suggestions only. Review and apply them in the product form before saving.").provider("deterministic-enrichment").build();
    }
    private String title(String value) { return Arrays.stream(value.trim().split("\\s+")).map(word -> word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1)).collect(java.util.stream.Collectors.joining(" ")); }
}
