package toyGroupChat.domain.file.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadedReqDto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// S3에 프로필 이미지 업로드가 완료됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class ProfileImageUploaded extends AbstractEvent {
    private Long id;
    private Long userId;
    private String name;
    private String url;
    private Date createdDate;

    public ProfileImageUploaded(MockProfileImageUploadedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.userId = mockData.getUserId();
        this.name = mockData.getName();
        this.url = mockData.getUrl();
        this.createdDate = mockData.getCreatedDate();
    }

    public ProfileImageUploaded() {
        super();
    }
}
