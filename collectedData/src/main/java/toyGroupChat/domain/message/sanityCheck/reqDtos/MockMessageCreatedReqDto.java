package toyGroupChat.domain.message.sanityCheck.reqDtos;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockMessageCreatedReqDto {
    private Long id;
    private Long roomId;
    private Long userId;
    private String content;
    private Long fileId;
    private Date createdDate;
}
