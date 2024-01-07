package toyGroupChat.domain.room.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.room.room.Room;
import toyGroupChat.domain.room.room.RoomRepository;
import toyGroupChat.domain.room.roomUser.RoomUser;
import toyGroupChat.domain.room.roomUser.RoomUserRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    
    // 내가 속한 그룹 채팅 방에 대한 정보들을 반환시키기 위해서
    public List<Room> joinedRooms(Long userId) {
        List<RoomUser> roomUsers = this.roomUserRepository.findAllByUserId(userId);
        
        List<Room> joinedRooms = new ArrayList<>();
        for(RoomUser roomUser : roomUsers) {
            Optional<Room> optionalRoom = this.roomRepository.findByRoomId(roomUser.getRoomId());
            if(!(optionalRoom.isPresent())) continue;
            joinedRooms.add(optionalRoom.get());
        }

        return joinedRooms;
    }
}
