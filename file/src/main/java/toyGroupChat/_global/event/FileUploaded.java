package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.File;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// S3에 메세지 파일 업로드가 완료됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class FileUploaded extends AbstractEvent {
    private Long id;
    private Long messageId;
    private String name;
    private String url;
    private Date createdDate;

    public FileUploaded(File aggregate, Long messageId) {
        super(aggregate);
        this.messageId = messageId;
    }

    public FileUploaded() {
        super();
    }
}
