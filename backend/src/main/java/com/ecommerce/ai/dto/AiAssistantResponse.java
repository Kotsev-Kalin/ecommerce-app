package com.ecommerce.ai.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AiAssistantResponse(String answer, List<AiProductReference> recommendations,
                                  List<AiProductReference> sources, String provider) {}
