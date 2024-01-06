package toyGroupChat.domain.file.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockFileUploadFailedReqDto {
    private Long id;
    private Long messageId;
}
