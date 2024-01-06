package toyGroupChat.domain.room.sanityCheck;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.room.event.RoomCreated;
import toyGroupChat.domain.room.event.RoomCreaterAdded;
import toyGroupChat.domain.room.event.RoomUserAdded;
import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomCreatedReqDto;
import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomCreaterAddedReqDto;
import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomUserAddedReqDto;

@Service
@RequiredArgsConstructor
public class RoomSanityCheckService {

    // Policy 테스트용으로 RoomCreated 이벤트를 강제로 발생시키기 위해서
    public void mockRoomCreated(MockRoomCreatedReqDto mockData) {
        (new RoomCreated(mockData)).publish();
    }

    // Policy 테스트용으로 RoomCreaterAdded 이벤트를 강제로 발생시키기 위해서
    public void mockRoomCreaterAdded(MockRoomCreaterAddedReqDto mockData) {
        (new RoomCreaterAdded(mockData)).publish();
    }

    // Policy 테스트용으로 RoomUserAdded 이벤트를 강제로 발생시키기 위해서
    public void mockRoomUserAdded(MockRoomUserAddedReqDto mockData) {
        (new RoomUserAdded(mockData)).publish();
    }

}
