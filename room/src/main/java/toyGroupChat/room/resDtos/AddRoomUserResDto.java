package toyGroupChat.room.resDtos;

import lombok.Getter;
import lombok.ToString;

import toyGroupChat.domain.Room;

@Getter
@ToString
public class AddRoomUserResDto {
    private final Long id;

    public AddRoomUserResDto(Room room) {
        this.id = room.getId();
    }
}
