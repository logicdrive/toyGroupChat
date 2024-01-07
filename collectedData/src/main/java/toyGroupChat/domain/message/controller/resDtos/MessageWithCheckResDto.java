package toyGroupChat.domain.message.controller.resDtos;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

import toyGroupChat.domain.message.message.Message;

@Getter
@ToString
public class MessageWithCheckResDto {
    private final Long id;
    private final Long messageId;
    private final Long roomId;
    private final Long userId;
    private final String content;
    private final Long fileId;
    private final Date createdDate;
    private final String status;
    private final int unwatchCount;

    public MessageWithCheckResDto(Message message, int unwatchCount) {
        this.id = message.getId();
        this.messageId = message.getMessageId();
        this.roomId = message.getRoomId();
        this.userId = message.getUserId();
        this.content = message.getContent();
        this.fileId = message.getFileId();
        this.createdDate = message.getCreatedDate();
        this.status = message.getStatus();
        this.unwatchCount = unwatchCount;
    }
}