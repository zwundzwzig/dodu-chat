package com.project.doduchat.controller;

import com.project.doduchat.config.auth.LoginUser;
import com.project.doduchat.config.auth.SessionUser;
import com.project.doduchat.domain.Chat;
import com.project.doduchat.service.ChatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@Log4j2
public class ChatController {

//    @Autowired
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Model model, HttpServletResponse res, @LoginUser SessionUser user) throws Exception {
        try {
            Optional<Chat> chat = chatService.findById(id, user);
            model.addAttribute("chat", chat);
            model.addAttribute("user", user); //session user
            log.info("@ChatController, chat GET()");
        } catch (Exception err) {
            String message = URLEncoder.encode(err.getMessage(), StandardCharsets.UTF_8);
            String redirectUrl = "/?alert=true&message=" + message;
            res.sendRedirect(redirectUrl);
        }
        return "chat";
    }

    @GetMapping("/chatList")
    public String chatlist(Model model, @LoginUser SessionUser user) {
        model.addAttribute("user", user);
        model.addAttribute("chatlist", chatService.getAllChatListByMentee(user.getId()));
        return "chat-list";
    }

    @GetMapping("/chatgpt")
    public String chatgpt(Model model, @LoginUser SessionUser user) {
        model.addAttribute("user", user);
        return "chatgpt";
    }

//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/chat/{chat_id}")
//    public ResponseEntity<ChatLogDTO> postChatLog(@PathVariable("chat_id") Long chat_id, @RequestBody ChatLogDTO chatLogDTO){
//        ChatLogDTO createdDto = chatService.postChatLog(chat_id, chatLogDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
//    }
}
