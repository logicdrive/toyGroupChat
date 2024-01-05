package toyGroupChat.sanityCheck.reqDtos;

import java.util.Date;

import lombok.Data;

@Data
public class MockFileUploadedReqDto {
    private Long id;
    private Long messageId;
    private String name;
    private String url;
    private Date createdDate;
}
