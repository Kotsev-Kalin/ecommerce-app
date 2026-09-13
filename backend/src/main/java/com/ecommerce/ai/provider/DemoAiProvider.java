package com.ecommerce.ai.provider;

import com.ecommerce.ai.dto.AiProductReference;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.ai.provider", havingValue = "demo", matchIfMissing = true)
public class DemoAiProvider implements AiProvider {
    public String name() { return "demo"; }
    public String answerShoppingQuestion(String question, List<AiProductReference> sources) {
        if (sources.isEmpty()) return "I could not find an in-stock catalog product matching that question. Try different product or category keywords.";
        return "I found matching in-stock catalog products for \"" + question.trim() + "\": "
                + sources.stream().map(AiProductReference::name).collect(Collectors.joining(", "))
                + ". Review the product details and sources below before choosing.";
    }
}
