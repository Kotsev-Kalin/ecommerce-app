package com.ecommerce.ai.provider;

import com.ecommerce.ai.dto.AiProductReference;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnProperty(name = "app.ai.provider", havingValue = "openai-compatible")
public class OpenAiCompatibleProvider implements AiProvider {
    private final RestClient client; private final String model;
    public OpenAiCompatibleProvider(@org.springframework.beans.factory.annotation.Value("${app.ai.openai.base-url}") String url,
                                    @org.springframework.beans.factory.annotation.Value("${app.ai.openai.api-key}") String key,
                                    @org.springframework.beans.factory.annotation.Value("${app.ai.openai.model}") String model) {
        if (!StringUtils.hasText(key)) throw new IllegalStateException("AI_API_KEY is required when AI_PROVIDER=openai-compatible");
        client = RestClient.builder().baseUrl(url).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key).build(); this.model = model;
    }
    @SuppressWarnings("unchecked") public String answerShoppingQuestion(String question, List<AiProductReference> sources) {
        String catalog = sources.stream().map(p -> p.name() + " | price=" + p.price() + " | stock=" + p.stockQuantity()).reduce("", (a, b) -> a + "\n" + b);
        Map<String, Object> body = Map.of("model", model, "messages", List.of(Map.of("role", "system", "content", "Answer only from the supplied catalog. Do not invent prices, availability, policies, or attributes."), Map.of("role", "user", "content", "Question: " + question + "\nCatalog:" + catalog)), "temperature", 0);
        Map<String, Object> response = client.post().uri("/chat/completions").contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices == null || choices.isEmpty()) throw new IllegalStateException("AI provider returned no completion choices");
        Map<String, Object> message = (Map<String, Object>) choices.getFirst().get("message");
        if (!(message.get("content") instanceof String content) || content.isBlank()) throw new IllegalStateException("AI provider returned an empty completion");
        return content;
    }
    public String name() { return "openai-compatible"; }
}
