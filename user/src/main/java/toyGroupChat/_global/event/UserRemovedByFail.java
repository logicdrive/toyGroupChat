package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 회원가입 처리 실패시에 정보가 삭제 되었음을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class UserRemovedByFail extends AbstractEvent {
    private Long id;

    public UserRemovedByFail(User aggregate) {
        super(aggregate);
    }

    public UserRemovedByFail() {
        super();
    }
}
