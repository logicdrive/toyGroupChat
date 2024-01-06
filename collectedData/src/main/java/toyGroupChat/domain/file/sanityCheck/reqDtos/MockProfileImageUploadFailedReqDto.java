package toyGroupChat.domain.file.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockProfileImageUploadFailedReqDto {
    private Long id;
    private Long userId;
}
