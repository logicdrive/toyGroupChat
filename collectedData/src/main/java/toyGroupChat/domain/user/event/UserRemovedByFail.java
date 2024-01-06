package toyGroupChat.domain.user.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockUserRemovedByFailReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 회원가입 처리 실패시에 정보가 삭제 되었음을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class UserRemovedByFail extends AbstractEvent {
    private Long id;
    
    public UserRemovedByFail(MockUserRemovedByFailReqDto mockData) {
        super();
        this.id = mockData.getId();
    }

    public UserRemovedByFail() {
        super();
    }
}
