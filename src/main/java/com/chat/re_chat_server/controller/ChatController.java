package com.chat.re_chat_server.controller;

import com.chat.re_chat_server.dto.ChatMessage;
import com.chat.re_chat_server.repository.ChatRoomRepository;
import com.chat.re_chat_server.service.ChatService;
import com.chat.re_chat_server.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        log.info("/pub/chat/message 메소드 실행");
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        //로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        //채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        chatService.sendChatMessage(message);
    }
}
