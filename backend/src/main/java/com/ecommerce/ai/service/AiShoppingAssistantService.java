package com.ecommerce.ai.service;

import com.ecommerce.ai.dto.AiAssistantRequest;
import com.ecommerce.ai.dto.AiAssistantResponse;
import com.ecommerce.ai.dto.AiProductReference;
import com.ecommerce.ai.provider.AiProvider;
import com.ecommerce.ai.search.ProductSearchService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AiShoppingAssistantService {
    private final ProductSearchService search; private final AiProvider provider;
    public AiShoppingAssistantService(ProductSearchService search, AiProvider provider) { this.search = search; this.provider = provider; }
    public AiAssistantResponse answer(AiAssistantRequest request) {
        List<AiProductReference> matches = search.search(request.getQuestion(), 3);
        return AiAssistantResponse.builder().answer(provider.answerShoppingQuestion(request.getQuestion(), matches))
                .recommendations(matches).sources(matches).provider(provider.name()).build();
    }
}
