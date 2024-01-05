package toyGroupChat.sanityCheck.reqDtos;

import lombok.Data;

@Data
public class MockProfileImageUploadRequestedReqDto {
    private Long id;
    private String dataUrl;

    public String toString() { 
        return String.format("%s(id=%s, dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.id, this.dataUrl.length());
    }
}
