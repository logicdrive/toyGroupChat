package toyGroupChat.domain.room.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomUserAddedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 공유 코드로 인해서 유저가 채팅룸 유저 목록에 추가되었음을 알리는 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class RoomUserAdded extends AbstractEvent {
    private Long id;
    private Long roomId;
    private Long userId;

    public RoomUserAdded(MockRoomUserAddedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.roomId = mockData.getRoomId();
        this.userId = mockData.getUserId();
    }

    public RoomUserAdded() {
        super();
    }
}
