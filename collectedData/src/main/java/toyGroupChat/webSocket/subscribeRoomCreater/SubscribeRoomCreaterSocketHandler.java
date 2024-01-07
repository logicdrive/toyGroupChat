package toyGroupChat.webSocket.subscribeRoomCreater;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.room.roomUser.RoomUser;
import toyGroupChat.domain.room.roomUser.RoomUserRepository;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

// 특정 회원가입 상태를 감시하고, 상태 변화를 알리기 위해서
@Component
@RequiredArgsConstructor
public class SubscribeRoomCreaterSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();
    private final RoomUserRepository roomUserRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SubscribeRoomCreaterReqDto subscribeCreateRoomReqDto = new SubscribeRoomCreaterReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{subscribeCreateRoomReqDto: %s}", subscribeCreateRoomReqDto.toString()));


            // 이미 그룹 채팅방에 생성자가 추가된 경우 메세지 보내기
            Optional<RoomUser> optionalRoomUser = this.roomUserRepository.findByRoomId(subscribeCreateRoomReqDto.getRoomId());
            if(optionalRoomUser.isPresent()) {
                
                SubscribeRoomCreaterResDto subscribeRoomCreaterResDto = new SubscribeRoomCreaterResDto(optionalRoomUser.get().getRoomId(), optionalRoomUser.get().getUserId());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify Added Room Creater", String.format("{subscribeRoomCreaterResDto: %s}", subscribeRoomCreaterResDto.toString()));

                session.sendMessage(subscribeRoomCreaterResDto.jsonTextMessage());
                return;
            }


            // 그룹 채팅방에 아직 생성자가 없을 경우 구독
            if(!this.subscribes.containsKey(subscribeCreateRoomReqDto.getRoomId()))
                this.subscribes.put(subscribeCreateRoomReqDto.getRoomId(), new HashMap<>());
            
            if(!this.subscribes.get(subscribeCreateRoomReqDto.getRoomId()).containsKey(session.getId()))
                this.subscribes.get(subscribeCreateRoomReqDto.getRoomId()).put(session.getId(), session);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }

    public void notifyRoomUserUpdate(RoomUser roomUser) {
        if(!this.subscribes.containsKey(roomUser.getRoomId())) return;

        try {

            HashMap<String, WebSocketSession> subscribedSessions = this.subscribes.get(roomUser.getRoomId());
            for(String sessionId : subscribedSessions.keySet()) {
                WebSocketSession session = subscribedSessions.get(sessionId);
                if(!session.isOpen())
                {
                    subscribedSessions.remove(sessionId);
                    return;
                }


                SubscribeRoomCreaterResDto subscribeRoomCreaterResDto = new SubscribeRoomCreaterResDto(roomUser.getRoomId(), roomUser.getUserId());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify Added Room Creater", String.format("{subscribeRoomCreaterResDto: %s}", subscribeRoomCreaterResDto.toString()));

                session.sendMessage(subscribeRoomCreaterResDto.jsonTextMessage());
            }

            this.subscribes.remove(roomUser.getRoomId());

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{roomUser: %s}", roomUser.toString()));
        }
    }
}
