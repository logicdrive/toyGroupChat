package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 프로필 이미지가 업로드되고, 회원가입 정보 등록이 완료됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class SignUpCompleted extends AbstractEvent {
    private Long id;
    private String name;
    private String email;
    private Long profileImageFileId;

    public SignUpCompleted(User aggregate) {
        super(aggregate);
    }

    public SignUpCompleted() {
        super();
    }
}
