package toyGroupChat.domain.room.controller.resDtos;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import toyGroupChat.domain.room.room.Room;

@Getter
@ToString
public class JoinedRoomResDto {
    private final Long id;
    private final Long roomId;
    private final String name;
    private final String sharedCode;
    private final Date createdDate;

    public JoinedRoomResDto(Room room) {
        this.id = room.getId();
        this.roomId = room.getRoomId();
        this.name = room.getName();
        this.sharedCode = room.getSharedCode();
        this.createdDate = room.getCreatedDate();
    }
}