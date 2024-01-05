package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.Message;

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

    public MessageCreated(Message aggregate) {
        super(aggregate);
    }

    public MessageCreated() {
        super();
    }
}
