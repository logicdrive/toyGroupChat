package toyGroupChat.room.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddRoomUserReqDto {
    private String sharedCode;
}