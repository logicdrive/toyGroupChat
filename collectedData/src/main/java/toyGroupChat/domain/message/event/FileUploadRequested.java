package toyGroupChat.domain.message.event;

import toyGroupChat._global.infra.AbstractEvent;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockFileUploadRequestedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

// 메세지에 파일 포함시 그 파일을 S3에 업로드하기 위한 이벤트
@Data
@EqualsAndHashCode(callSuper=false)
public class FileUploadRequested extends AbstractEvent {
    private Long id;
    private String name;
    private String dataUrl;

    public FileUploadRequested(MockFileUploadRequestedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.name = mockData.getName();
        this.dataUrl = mockData.getDataUrl();
    }

    public FileUploadRequested() {
        super();
    }

    public String toString() { 
        return String.format("%s(id=%s, name=%s, dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.id, this.name, this.dataUrl.length());
    }
}
