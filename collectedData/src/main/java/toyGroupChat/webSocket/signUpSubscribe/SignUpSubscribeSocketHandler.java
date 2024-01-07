package toyGroupChat.webSocket.signUpSubscribe;

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
public class SignUpSubscribeSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();
    private final UserRepository userRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SignUpSubscribeReqDto signUpSubscribeReqDto = new SignUpSubscribeReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signUpSubscribeReqDto: %s}", signUpSubscribeReqDto.toString()));


            // 이미 해당하는 회원가입 정보가 있을 경우 처리
            Optional<User> optionalUser = this.userRepository.findByUserId(signUpSubscribeReqDto.getUserId());
            if(optionalUser.isPresent() &&
              (optionalUser.get().getStatus().equals("SignUpCompleted") || optionalUser.get().getStatus().equals("UserRemovedByFail"))) {
                
                SignUpSubscribeResDto signUpSubscribeResDto = new SignUpSubscribeResDto(signUpSubscribeReqDto.getUserId(), optionalUser.get().getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed signuped User status", String.format("{signUpSubscribeResDto: %s}", signUpSubscribeResDto.toString()));

                session.sendMessage(signUpSubscribeResDto.jsonTextMessage());
                return;
            }


            // 회원가입 정보가 없을 경우, 해당 ID로 구독함
            if(!this.subscribes.containsKey(signUpSubscribeReqDto.getUserId()))
                this.subscribes.put(signUpSubscribeReqDto.getUserId(), new HashMap<>());
            
            if(!this.subscribes.get(signUpSubscribeReqDto.getUserId()).containsKey(session.getId()))
                this.subscribes.get(signUpSubscribeReqDto.getUserId()).put(session.getId(), session);

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
                    return;
                }

                SignUpSubscribeResDto signUpSubscribeResDto = new SignUpSubscribeResDto(user.getUserId(), user.getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed signuped User status", String.format("{signUpSubscribeResDto: %s}", signUpSubscribeResDto.toString()));

                session.sendMessage(signUpSubscribeResDto.jsonTextMessage());
            }

            if((user.getStatus().equals("SignUpCompleted")) || (user.getStatus().equals("UserRemovedByFail"))) {
                this.subscribes.remove(user.getUserId());
            }

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{user: %s}", user.toString()));
        }
    }
}
