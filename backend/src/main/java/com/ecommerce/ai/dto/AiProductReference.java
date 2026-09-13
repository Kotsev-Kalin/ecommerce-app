package com.ecommerce.ai.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record AiProductReference(Long productId, String name, String categoryName, BigDecimal price,
                                 String imageUrl, Integer stockQuantity, String source) {}
