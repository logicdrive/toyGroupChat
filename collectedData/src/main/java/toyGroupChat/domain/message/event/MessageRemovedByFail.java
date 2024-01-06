package toyGroupChat.domain.message.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageRemovedByFailReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 메세지 파일 업로드 실패로 인해서 메세지가 삭제됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class MessageRemovedByFail extends AbstractEvent {
    private Long id;

    public MessageRemovedByFail(MockMessageRemovedByFailReqDto mockData) {
        super();
        this.id = mockData.getId();
    }

    public MessageRemovedByFail() {
        super();
    }
}
