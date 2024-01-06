package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.RoomUser;

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

    public RoomUserAdded(RoomUser aggregate) {
        super(aggregate);
    }

    public RoomUserAdded() {
        super();
    }
}
