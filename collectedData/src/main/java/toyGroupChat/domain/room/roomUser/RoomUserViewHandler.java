package toyGroupChat.domain.room.roomUser;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.room.event.RoomCreaterAdded;
import toyGroupChat.domain.room.event.RoomUserAdded;

@Service
@RequiredArgsConstructor
public class RoomUserViewHandler {
    private final RoomUserRepository roomUserRepository;

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomCreaterAdded'"
    )
    public void whenRoomCreaterAdded_then_CREATE_1(
        @Payload RoomCreaterAdded roomCreaterAdded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{roomCreaterAdded: %s}", roomCreaterAdded.toString()));
            if (!roomCreaterAdded.validate()) return;

            RoomUser savedRoomUser = this.roomUserRepository.save(
                RoomUser.builder()
                    .roomUserId(roomCreaterAdded.getId())
                    .roomId(roomCreaterAdded.getRoomId())
                    .userId(roomCreaterAdded.getUserId())
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedRoomUser: %s}", savedRoomUser.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{roomCreaterAdded: %s}", roomCreaterAdded.toString()));
        }
    }

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomUserAdded'"
    )
    public void whenRoomUserAdded_then_CREATE_2(
        @Payload RoomUserAdded roomUserAdded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{roomUserAdded: %s}", roomUserAdded.toString()));
            if (!roomUserAdded.validate()) return;

            RoomUser savedRoomUser = this.roomUserRepository.save(
                RoomUser.builder()
                    .roomUserId(roomUserAdded.getId())
                    .roomId(roomUserAdded.getRoomId())
                    .userId(roomUserAdded.getUserId())
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedRoomUser: %s}", savedRoomUser.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{roomUserAdded: %s}", roomUserAdded.toString()));
        }
    }
}
