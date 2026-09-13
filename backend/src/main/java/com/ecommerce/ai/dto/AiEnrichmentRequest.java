package com.ecommerce.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AiEnrichmentRequest {
    @NotBlank(message = "A product title is required")
    @Size(max = 200, message = "Product title must not exceed 200 characters")
    private String title;
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;
    @Size(max = 120, message = "Category must not exceed 120 characters")
    private String category;
    @Size(max = 2048, message = "Image URL must not exceed 2048 characters")
    private String imageUrl;
}
