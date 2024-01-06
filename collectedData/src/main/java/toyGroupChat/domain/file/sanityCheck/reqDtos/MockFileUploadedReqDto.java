package toyGroupChat.domain.file.sanityCheck.reqDtos;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockFileUploadedReqDto {
    private Long id;
    private Long messageId;
    private String name;
    private String url;
    private Date createdDate;
}
