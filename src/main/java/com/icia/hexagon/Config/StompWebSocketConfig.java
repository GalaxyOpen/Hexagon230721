package com.icia.hexagon.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //Stomp 사용 어노테이션
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8086")
                .withSockJS();
    }

    // 어플리케이션 내부에서 사용할 path를 지정할 수 있음.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //Client 에서 Send요청을 처리함.
        //경로의 경우 스프링 Docs는 /topic, /queue로 확인되나, /pub, /sub로 변경

        registry.setApplicationDestinationPrefixes("/pub");
        // 위의 경로로 SimpleBroker를 등록.
        // SimpleBroker는 해당하는 경로를 Subscribe하는 클라이언트에게 메세지를 전달하는 간단한 작업을 수행.


        registry.enableSimpleBroker("/sub");
        //enableStompBrokerRelay
        //SimipleBroker의 기능과 외부 message Broker(RabbitMQ, ActiveMQ 등)에 메세지를 전달하는 기능을 가짐
    }
}
