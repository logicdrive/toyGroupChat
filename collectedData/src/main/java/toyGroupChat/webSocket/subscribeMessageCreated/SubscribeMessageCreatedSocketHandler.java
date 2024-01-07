package toyGroupChat.webSocket.subscribeMessageCreated;
import java.io.IOException;
import java.util.HashMap;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.domain.message.message.Message;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

// 특정 룸에서 메세지의 추가 여부를 실시간으로 확인하기 위해서
@Component
@RequiredArgsConstructor
public class SubscribeMessageCreatedSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SubscribeMessageCreatedReqDto subscribeMessageCreatedReqDto = new SubscribeMessageCreatedReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{subscribeMessageCreatedReqDto: %s}", subscribeMessageCreatedReqDto.toString()));


            if(!this.subscribes.containsKey(subscribeMessageCreatedReqDto.getRoomId()))
                this.subscribes.put(subscribeMessageCreatedReqDto.getRoomId(), new HashMap<>());
            
            if(!this.subscribes.get(subscribeMessageCreatedReqDto.getRoomId()).containsKey(session.getId()))
                this.subscribes.get(subscribeMessageCreatedReqDto.getRoomId()).put(session.getId(), session);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }

    public void notifyMessageCreated(Message message) {
        if(!this.subscribes.containsKey(message.getRoomId())) return;

        try {

            HashMap<String, WebSocketSession> subscribedSessions = this.subscribes.get(message.getRoomId());
            for(String sessionId : subscribedSessions.keySet()) {
                WebSocketSession session = subscribedSessions.get(sessionId);
                if(!session.isOpen())
                {
                    subscribedSessions.remove(sessionId);
                    return;
                }


                SubscribeMessageCreatedResDto subscribeMessageCreatedResDto = new SubscribeMessageCreatedResDto(message.getMessageId(), message.getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify Created Message", String.format("{subscribeMessageCreatedResDto: %s}", subscribeMessageCreatedResDto.toString()));

                session.sendMessage(subscribeMessageCreatedResDto.jsonTextMessage());
            }

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }
}
