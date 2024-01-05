package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.sanityCheck.reqDtos.MockFileUploadedReqDto;

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

    public FileUploaded(MockFileUploadedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.messageId = mockData.getMessageId();
        this.name = mockData.getName();
        this.url = mockData.getUrl();
        this.createdDate = mockData.getCreatedDate();
    }

    public FileUploaded() {
        super();
    }
}
