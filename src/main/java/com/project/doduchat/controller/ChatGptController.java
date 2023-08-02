package com.project.doduchat.controller;

import com.project.doduchat.domain.dto.ChatGptResponseDTO;
import com.project.doduchat.domain.dto.QuestionRequestDTO;
import com.project.doduchat.service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatgpt")
public class ChatGptController {
    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService){
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDTO sendQuestion(@RequestBody QuestionRequestDTO requestDTO){
        return chatGptService.askQuestion(requestDTO);
    }

}