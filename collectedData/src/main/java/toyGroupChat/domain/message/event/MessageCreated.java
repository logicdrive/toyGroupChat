package toyGroupChat.domain.message.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageCreatedReqDto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

// 메세지가 완전히 생성되었을 경우 이를 알리는 이벤트
@Data
@EqualsAndHashCode(callSuper=false)
public class MessageCreated extends AbstractEvent {
    private Long id;
    private Long roomId;
    private Long userId;
    private String content;
    private Long fileId;
    private Date createdDate;

    public MessageCreated(MockMessageCreatedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.roomId = mockData.getRoomId();
        this.userId = mockData.getUserId();
        this.content = mockData.getContent();
        this.fileId = mockData.getFileId();
        this.createdDate = mockData.getCreatedDate();
    }

    public MessageCreated() {
        super();
    }
}
