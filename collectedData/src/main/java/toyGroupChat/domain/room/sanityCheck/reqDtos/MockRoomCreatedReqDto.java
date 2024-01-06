package toyGroupChat.domain.room.sanityCheck.reqDtos;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockRoomCreatedReqDto {
    private Long id;
    private Long createrUserId;
    private String name;
    private String sharedCode;
    private Date createdDate;
}
