package toyGroupChat.webSocket.subscribeSignUp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.domain.user.User;
import toyGroupChat.domain.user.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

// 특정 회원가입 상태를 감시하고, 상태 변화를 알리기 위해서
@Component
@RequiredArgsConstructor
public class SubscribeSignUpSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();
    private final UserRepository userRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SubscribeSignUpReqDto subscribeSignUpReqDto = new SubscribeSignUpReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{subscribeSignUpReqDto: %s}", subscribeSignUpReqDto.toString()));


            // 이미 해당하는 회원가입 정보가 있을 경우 처리
            Optional<User> optionalUser = this.userRepository.findByUserId(subscribeSignUpReqDto.getUserId());
            if(optionalUser.isPresent() &&
              (optionalUser.get().getStatus().equals("SignUpCompleted") || optionalUser.get().getStatus().equals("UserRemovedByFail"))) {
                
                SubscribeSignUpResDto subscribeSignUpResDto = new SubscribeSignUpResDto(subscribeSignUpReqDto.getUserId(), optionalUser.get().getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed signuped User status", String.format("{subscribeSignUpResDto: %s}", subscribeSignUpResDto.toString()));

                session.sendMessage(subscribeSignUpResDto.jsonTextMessage());
                return;
            }


            // 회원가입 정보가 없을 경우, 해당 ID로 구독함
            if(!this.subscribes.containsKey(subscribeSignUpReqDto.getUserId()))
                this.subscribes.put(subscribeSignUpReqDto.getUserId(), new HashMap<>());
            
            if(!this.subscribes.get(subscribeSignUpReqDto.getUserId()).containsKey(session.getId()))
                this.subscribes.get(subscribeSignUpReqDto.getUserId()).put(session.getId(), session);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }

    public void notifyUserUpdate(User user) {
        if(!this.subscribes.containsKey(user.getUserId())) return;

        try {

            HashMap<String, WebSocketSession> subscribedSessions = this.subscribes.get(user.getUserId());
            for(String sessionId : subscribedSessions.keySet()) {
                WebSocketSession session = subscribedSessions.get(sessionId);
                if(!session.isOpen())
                {
                    subscribedSessions.remove(sessionId);
                    continue;
                }

                SubscribeSignUpResDto subscribeSignUpResDto = new SubscribeSignUpResDto(user.getUserId(), user.getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed signuped User status", String.format("{subscribeSignUpResDto: %s}", subscribeSignUpResDto.toString()));

                session.sendMessage(subscribeSignUpResDto.jsonTextMessage());
            }

            if((user.getStatus().equals("SignUpCompleted")) || (user.getStatus().equals("UserRemovedByFail"))) {
                this.subscribes.remove(user.getUserId());
            }

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{user: %s}", user.toString()));
        }
    }
}
