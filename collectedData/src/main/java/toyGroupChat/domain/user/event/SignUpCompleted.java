package toyGroupChat.domain.user.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockSignUpCompletedReqDto;

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

    public SignUpCompleted(MockSignUpCompletedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.name = mockData.getName();
        this.email = mockData.getEmail();
        this.profileImageFileId = mockData.getProfileImageFileId();
    }

    public SignUpCompleted() {
        super();
    }
}
