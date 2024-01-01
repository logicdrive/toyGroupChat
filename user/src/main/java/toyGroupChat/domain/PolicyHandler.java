package toyGroupChat.domain;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toyGroupChat._global.config.kafka.KafkaProcessor;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}
}
