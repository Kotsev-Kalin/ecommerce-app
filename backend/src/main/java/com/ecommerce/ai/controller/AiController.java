package com.ecommerce.ai.controller;

import com.ecommerce.ai.dto.*;
import com.ecommerce.ai.service.AiProductEnrichmentService;
import com.ecommerce.ai.service.AiShoppingAssistantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/ai")
public class AiController {
    private final AiShoppingAssistantService assistant; private final AiProductEnrichmentService enrichment;
    public AiController(AiShoppingAssistantService assistant, AiProductEnrichmentService enrichment) { this.assistant = assistant; this.enrichment = enrichment; }
    @PostMapping("/assistant") public AiAssistantResponse assistant(@Valid @RequestBody AiAssistantRequest request) { return assistant.answer(request); }
    @PostMapping("/admin/product-enrichment") public AiEnrichmentResponse enrich(@Valid @RequestBody AiEnrichmentRequest request) { return enrichment.suggest(request); }
}
