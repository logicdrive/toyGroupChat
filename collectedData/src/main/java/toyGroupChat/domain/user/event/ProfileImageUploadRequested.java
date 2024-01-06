package toyGroupChat.domain.user.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockProfileImageUploadRequestedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

// 회원가입 요청시에 프로필 이미지를 S3에 업로드하기 위한 이벤트
@Data
@EqualsAndHashCode(callSuper=false)
public class ProfileImageUploadRequested extends AbstractEvent {
    private Long id;
    private String dataUrl;

    public ProfileImageUploadRequested(MockProfileImageUploadRequestedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.dataUrl = mockData.getDataUrl();
    }

    public ProfileImageUploadRequested() {
        super();
    }

    public String toString() { 
        return String.format("%s(id=%s, dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.id, this.dataUrl.length());
    }
}
