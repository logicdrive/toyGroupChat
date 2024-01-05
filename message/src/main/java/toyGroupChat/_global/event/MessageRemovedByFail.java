package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.Message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 메세지 파일 업로드 실패로 인해서 메세지가 삭제됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class MessageRemovedByFail extends AbstractEvent {
    private Long id;

    public MessageRemovedByFail(Message aggregate) {
        super(aggregate);
    }

    public MessageRemovedByFail() {
        super();
    }
}
