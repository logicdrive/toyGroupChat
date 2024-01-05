package toyGroupChat.message;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.event.FileUploadRequested;
import toyGroupChat._global.event.MessageCreated;

import toyGroupChat.domain.Message;
import toyGroupChat.domain.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    
    public Message createMessage(CreateMessageReqDto createMessageReqDto, Long userId) {
        Message savedMessage = this.messageRepository.save(
                Message.builder()
                    .roomId(createMessageReqDto.getRoomId())
                    .userId(userId)
                    .content(createMessageReqDto.getContent())
                    .build()
            );
        
        if((createMessageReqDto.getFileName() != null) && (createMessageReqDto.getFileName().length() > 0)) {

            FileUploadRequested fileUploadRequested = new FileUploadRequested();
            fileUploadRequested.setId(savedMessage.getId());
            fileUploadRequested.setName(createMessageReqDto.getFileName());
            fileUploadRequested.setDataUrl(createMessageReqDto.getFileDataUrl());
            fileUploadRequested.publishAfterCommit();

        }
        else {

            MessageCreated messageCreated = new MessageCreated(savedMessage);
            messageCreated.publishAfterCommit();

        }

        return savedMessage;
    }
}
