package toyGroupChat.domain.room.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomCreaterAddedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 채팅룸을 생성한 사람이 채팅룸 유저 목록에 추가되었음을 알리는 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class RoomCreaterAdded extends AbstractEvent {
    private Long id;
    private Long roomId;
    private Long userId;

    public RoomCreaterAdded(MockRoomCreaterAddedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.roomId = mockData.getRoomId();
        this.userId = mockData.getUserId();
    }

    public RoomCreaterAdded() {
        super();
    }
}
