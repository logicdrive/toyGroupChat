package toyGroupChat.room;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.event.RoomCreated;
import toyGroupChat._global.event.RoomUserAdded;
import toyGroupChat.domain.Room;
import toyGroupChat.domain.RoomRepository;
import toyGroupChat.domain.RoomUser;
import toyGroupChat.domain.RoomUserRepository;
import toyGroupChat.room.exceptions.RoomNotFoundException;
import toyGroupChat.room.reqDtos.AddRoomUserReqDto;
import toyGroupChat.room.reqDtos.CreateRoomReqDto;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    
    public Room createRoom(CreateRoomReqDto createMessageReqDto, Long userId) {
        Room savedRoom = this.roomRepository.save(
            Room.builder()
                .name(createMessageReqDto.getName())
                .build()
        );

        RoomCreated roomCreated = new RoomCreated(savedRoom, userId);
        roomCreated.publishAfterCommit();

        return savedRoom;
    }

    public Room addRoomUser(AddRoomUserReqDto addRoomUserReqDto, Long userId) {
        Room sharedRoom = this.roomRepository.findBySharedCode(addRoomUserReqDto.getSharedCode())
            .orElseThrow(() -> new RoomNotFoundException());

        RoomUser savedRoomUser = this.roomUserRepository.save(
            RoomUser.builder()
                .roomId(sharedRoom.getId())
                .userId(userId)
                .build()
        );

        RoomUserAdded roomUserAdded = new RoomUserAdded(savedRoomUser);
        roomUserAdded.publishAfterCommit();

        return sharedRoom;
    }
}
