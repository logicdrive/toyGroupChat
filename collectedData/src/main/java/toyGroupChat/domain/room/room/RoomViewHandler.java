package toyGroupChat.domain.room.room;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.room.event.RoomCreated;

@Service
@RequiredArgsConstructor
public class RoomViewHandler {
    private final RoomRepository roomRepository;

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomCreated'"
    )
    public void whenRoomCreated_then_CREATE_1(
        @Payload RoomCreated roomCreated
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{roomCreated: %s}", roomCreated.toString()));
            if (!roomCreated.validate()) return;

            Room savedRoom = this.roomRepository.save(
                Room.builder()
                    .roomId(roomCreated.getId())
                    .name(roomCreated.getName())
                    .sharedCode(roomCreated.getSharedCode())
                    .createdDate(roomCreated.getCreatedDate())
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedRoom: %s}", savedRoom.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{roomCreated: %s}", roomCreated.toString()));
        }
    }
}
