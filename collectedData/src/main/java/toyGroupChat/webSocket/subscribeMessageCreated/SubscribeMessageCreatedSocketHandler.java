package toyGroupChat.webSocket.subscribeMessageCreated;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.domain.message.message.Message;
import toyGroupChat.domain.message.message.MessageRepository;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

// 특정 회원가입 상태를 감시하고, 상태 변화를 알리기 위해서
@Component
@RequiredArgsConstructor
public class SubscribeMessageCreatedSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();
    private final MessageRepository messageRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SubscribeMessageCreatedReqDto subscribeMessageCreatedReqDto = new SubscribeMessageCreatedReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{subscribeMessageCreatedReqDto: %s}", subscribeMessageCreatedReqDto.toString()));


            // 이미 메세지가 추가된 경우, 관련된 소켓 응답 보내기
            Optional<Message> optionalMessage = this.messageRepository.findByMessageId(subscribeMessageCreatedReqDto.getMessageId());
            if(optionalMessage.isPresent() &&
              (optionalMessage.get().getStatus().equals("MessageCreated") || optionalMessage.get().getStatus().equals("UserRemovedByFail"))) {
                
                SubscribeMessageCreatedResDto subscribeMessageCreatedResDto = new SubscribeMessageCreatedResDto(optionalMessage.get().getMessageId(), optionalMessage.get().getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify Created Message", String.format("{subscribeMessageCreatedResDto: %s}", subscribeMessageCreatedResDto.toString()));

                session.sendMessage(subscribeMessageCreatedResDto.jsonTextMessage());
                return;
            }


            // 아직 메세지가 생성되지 않았을 경우, 구독
            if(!this.subscribes.containsKey(subscribeMessageCreatedReqDto.getMessageId()))
                this.subscribes.put(subscribeMessageCreatedReqDto.getMessageId(), new HashMap<>());
            
            if(!this.subscribes.get(subscribeMessageCreatedReqDto.getMessageId()).containsKey(session.getId()))
                this.subscribes.get(subscribeMessageCreatedReqDto.getMessageId()).put(session.getId(), session);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }

    public void notifyMessageCreated(Message message) {
        if(!this.subscribes.containsKey(message.getMessageId())) return;

        try {

            HashMap<String, WebSocketSession> subscribedSessions = this.subscribes.get(message.getMessageId());
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

            this.subscribes.remove(message.getMessageId());

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }
}
