package toyGroupChat.domain.message.message;

import java.util.Optional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.message.event.FileUploadRequested;
import toyGroupChat.domain.message.event.MessageCreated;
import toyGroupChat.domain.message.event.MessageRemovedByFail;

@Service
@RequiredArgsConstructor
public class MessageViewHandler {
    private final MessageRepository messageRepository;

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploadRequested'"
    )
    public void whenFileUploadRequested_then_CREATE_1(
        @Payload FileUploadRequested fileUploadRequested
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploadRequested: %s}", fileUploadRequested.toString()));
            if (!fileUploadRequested.validate()) return;

            Message savedMessage = this.messageRepository.save(
                Message.builder()
                    .messageId(fileUploadRequested.getId())
                    .status("FileUploadRequested")
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedMessage: %s}", savedMessage.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploadRequested: %s}", fileUploadRequested.toString()));
        }
    }

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MessageCreated'"
    )
    public void whenMessageCreated_then_CREATE_or_UPDATE_1(
        @Payload MessageCreated messageCreated
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{messageCreated: %s}", messageCreated.toString()));
            if (!messageCreated.validate()) return;


            Optional<Message> optionalMessage = this.messageRepository.findByMessageId(messageCreated.getId());
            Message messageToSave = ((optionalMessage.isPresent()) ? optionalMessage.get() : new Message());

            messageToSave.setMessageId(messageCreated.getId());
            messageToSave.setRoomId(messageCreated.getRoomId());
            messageToSave.setUserId(messageCreated.getUserId());
            messageToSave.setContent(messageCreated.getContent());
            messageToSave.setFileId(messageCreated.getFileId());
            messageToSave.setCreatedDate(messageCreated.getCreatedDate());
            messageToSave.setStatus("MessageCreated");
            this.messageRepository.save(messageToSave);


            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{messageToSave: %s}", messageToSave.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{messageCreated: %s}", messageCreated.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MessageRemovedByFail'"
    )
    public void whenMessageRemovedByFail_then_DELETE_1(
        @Payload MessageRemovedByFail messageRemovedByFail
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{messageRemovedByFail: %s}", messageRemovedByFail.toString()));
            if (!messageRemovedByFail.validate()) return;

            Optional<Message> optionalMessage = this.messageRepository.findByMessageId(messageRemovedByFail.getId());
            if(!(optionalMessage.isPresent())) return;

            this.messageRepository.delete(optionalMessage.get());
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{deletedMessage: %s}", optionalMessage.get().toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{messageRemovedByFail: %s}", messageRemovedByFail.toString()));
        }
    }
}
