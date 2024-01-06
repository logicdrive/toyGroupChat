package toyGroupChat.domain;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.event.RoomCreated;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 그룹 룸 생성시에 생성자를 그룹 룸 유저 목록에 추가시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomCreated'"
    )
    public void wheneverRoomCreated_AddRoomCreater(
        @Payload RoomCreated roomCreated
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{roomCreated: %s}", roomCreated.toString()));
            RoomUser.addRoomCreater(roomCreated);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{roomCreated: %s}", roomCreated.toString()));
        }
    }
}
