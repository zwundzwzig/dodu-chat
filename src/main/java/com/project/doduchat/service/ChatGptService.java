package com.project.doduchat.service;

import com.project.doduchat.config.ChatGptConfig;
import com.project.doduchat.dto.ChatGptRequestDTO;
import com.project.doduchat.dto.ChatGptResponseDTO;
import com.project.doduchat.dto.QuestionRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {
    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDTO, headers);
    }

    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDTOHttpEntity){
        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDTOHttpEntity,
                ChatGptResponseDTO.class
        );
        return responseEntity.getBody();
    }

    public ChatGptResponseDTO askQuestion(QuestionRequestDTO requestDTO) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(
                                ChatGptConfig.MODEL,
                                requestDTO.getQuestion(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }

}
