package toyGroupChat.domain.user.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockSignUpCompletedReqDto {
    private Long id;
    private String name;
    private String email;
    private Long profileImageFileId;
}
