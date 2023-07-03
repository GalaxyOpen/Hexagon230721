package com.icia.hexagon.Chatting;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();
    // 객체를 저장하기 위한 리스트. 접속한 클라이언트의 세션을 리스트에 추가하고, 접속이 해제되면 리스트에서 제거.

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload :" + payload);

        for(WebSocketSession sess: list){
            sess.sendMessage(message);
        }
    }
    /**
     *클라이언트로부터 테그슽 메시지가 도착하면 호출되는 메소드
     * 메시지의 내용을 가져와서 로그에 출력한 후, 리스트에 저장된 모든 세션에게 메시지를 전송.  */


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session+"클라이언트 접속");
    }
    /**
     * 클라이언트가 웹 소켓으로 접속했을 때 호출되는 메소드.
     * 클라이언트의 세션을 리스트에 추가하고, 접속 정보를 로그에 출력함.*/

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info(session + "클라이언트 접속해제");
        list.remove(session);
    }
    /**
     * 클라이언트가 접속을 해제했을 때 호출되는 메소드
     * 클라이언트의 세션을 리스트에서 제거하고, 접속 해제 정보를 로그에 출력함.*/
}
