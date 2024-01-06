package toyGroupChat.domain.room.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockRoomUserAddedReqDto {
    private Long id;
    private Long roomId;
    private Long userId;
}
