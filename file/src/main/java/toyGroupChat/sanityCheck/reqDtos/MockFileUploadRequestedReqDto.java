package toyGroupChat.sanityCheck.reqDtos;

import lombok.Data;

@Data
public class MockFileUploadRequestedReqDto {
    private Long id;
    private String name;
    private String dataUrl;

    public String toString() { 
        return String.format("%s(id=%s, name=%s, dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.id, this.name, this.dataUrl.length());
    }
}
