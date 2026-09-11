package com.ecommerce.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AiAssistantRequest {
    @NotBlank(message = "A question is required")
    @Size(max = 500, message = "Question must not exceed 500 characters")
    private String question;
}
