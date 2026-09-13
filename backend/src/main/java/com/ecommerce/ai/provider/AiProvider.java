package com.ecommerce.ai.provider;

import com.ecommerce.ai.dto.AiProductReference;
import java.util.List;

public interface AiProvider {
    String name();
    String answerShoppingQuestion(String question, List<AiProductReference> catalogSources);
}
