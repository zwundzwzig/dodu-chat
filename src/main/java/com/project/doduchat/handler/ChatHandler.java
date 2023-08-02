package com.project.doduchat.handler;

import com.project.doduchat.domain.dto.ChatLogDTO;
import com.project.doduchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();
    //  이 list의 session은 메세지를 받아보는 session들

    //DBSaver saver;
    private final ChatService chatService;

    // 여기 파라미터 안의 session이 보내는 사람의 session
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // log용
        String payload = message.getPayload(); //payload? 전송되는 데이터
        log.info("payload: " + payload);

        // chat의 id를 가져온다.
        Long chatId = Long.parseLong(message.getPayload().split(":")[0]);

        // 임시로 유저 아이디와 텍스트 나눠주기
        String[] values = message.getPayload().split(":");
        String userId = values[1]; // 유저의 아이디

        // 텍스트 내용 (수정 필요)
        StringBuilder builder = new StringBuilder();
        for(int i = 2; i < values.length; i++) {
            builder.append(values[i]);
        }
        String text = builder.toString();

        // 시간
        LocalDateTime now = LocalDateTime.now();

        // 메세지 전송
        session.sendMessage(message);

        // db에 적재
        ChatLogDTO chatLog = new ChatLogDTO(userId, text, now, chatId);
        chatService.postChatLog(chatLog);
    }

    //Client 접속시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session)throws Exception{
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }

    //Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }

}