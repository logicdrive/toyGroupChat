package toyGroupChat.domain.room.controller.resDtos;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;
import toyGroupChat.domain.room.room.Room;

@Getter
@ToString
public class JoinedRoomsResDto {
    private final List<JoinedRoomResDto> rooms;

    public JoinedRoomsResDto(List<Room> rooms) {
        this.rooms = rooms.stream()
            .map(room -> new JoinedRoomResDto(room))
            .collect(Collectors.toList());
    }
}