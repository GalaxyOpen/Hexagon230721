package com.icia.hexagon.Chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}

//@Configuration
//@RequiredArgsConstructor
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    private final ChatHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
//    }
//}

/**
 * @Configuration : 스프링의 설정 클래스임을 나타냄
 * @RequiredArgsConstructor :
 *  1. 생성자를 자동으로 생성해주는 기능.
 *  2. ChatHandler 객체를 생성자의 인자로 받아와서 필드에 할당함.
 *
 * @EnableWebSocket : WebSocket 을 활성화하기 위한 어노테이션. 스프링이 WebSocket 을 지원할 수 있도록 설정.
 * registerWebSocketHandlers 메소드
 * 1.WebSocketHandlerRegistry 를 통해 WebSocket 핸들러를 등록하는 메소드.
 * 2.chatHandler 객체를 "ws/chat"경로에 매핑하고, 모든 도메인에서의 접근을 허용하기 위해
 *   setAllowedOrigins("*")를 설정함.*/

