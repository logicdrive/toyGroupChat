package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.sanityCheck.reqDtos.MockFileUploadFailedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// S3에 메세지 파일 업로드가 실패됨을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class FileUploadFailed extends AbstractEvent {
    private Long id;
    private Long messageId;

    public FileUploadFailed(MockFileUploadFailedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.messageId = mockData.getMessageId();
    }

    public FileUploadFailed() {
        super();
    }
}
