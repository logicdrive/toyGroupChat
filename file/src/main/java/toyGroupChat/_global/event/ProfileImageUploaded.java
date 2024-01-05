package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.File;

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

    public ProfileImageUploaded(File aggregate, Long userId) {
        super(aggregate);
        this.userId = userId;
    }

    public ProfileImageUploaded() {
        super();
    }
}
