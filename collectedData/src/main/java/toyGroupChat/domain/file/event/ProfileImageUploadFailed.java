package toyGroupChat.domain.file.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadFailedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// S3에 프로필 이미지 업로드가 실패됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class ProfileImageUploadFailed extends AbstractEvent {
    private Long id;
    private Long userId;

    public ProfileImageUploadFailed(MockProfileImageUploadFailedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.userId = mockData.getUserId();
    }

    public ProfileImageUploadFailed() {
        super();
    }
}
