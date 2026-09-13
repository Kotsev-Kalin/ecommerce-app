package com.ecommerce.ai.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AiEnrichmentResponse(String suggestedTitle, String suggestedDescription,
                                   String suggestedCategory, List<String> suggestedTags,
                                   String suggestedAltText, String notice, String provider) {}
