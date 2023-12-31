package toyGroupChat.domain.file.sanityCheck.reqDtos;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockProfileImageUploadedReqDto {
    private Long id;
    private Long userId;
    private String name;
    private String url;
    private Date createdDate;
}
