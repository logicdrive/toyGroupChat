package toyGroupChat.room.resDtos;

import lombok.Getter;
import lombok.ToString;

import toyGroupChat.domain.Room;

@Getter
@ToString
public class CreateRoomResDto {
    private final Long id;

    public CreateRoomResDto(Room room) {
        this.id = room.getId();
    }
}
