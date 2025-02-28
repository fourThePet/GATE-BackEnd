package com.ureca.gate.global.config.gpt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ChatClintConfig {

    @Value("${spring.ai.openai.api-key}")
    private String key;


    @Bean
    public ChatModel openAiChatModel() {
        // OpenAI API 설정 (API 키 필요)
        OpenAiApi openAiApi = new OpenAiApi(key);
        return new OpenAiChatModel(openAiApi);
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
