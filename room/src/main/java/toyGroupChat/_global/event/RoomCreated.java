package toyGroupChat._global.event;

import toyGroupChat._global.infra.AbstractEvent;

import toyGroupChat.domain.Room;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 채팅룸 생성이 완료되었음을 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class RoomCreated extends AbstractEvent {
    private Long id;
    private Long createrUserId;
    private String name;
    private String sharedCode;
    private Date createdDate;

    public RoomCreated(Room aggregate, Long createrUserId) {
        super(aggregate);
        this.createrUserId = createrUserId;
    }

    public RoomCreated() {
        super();
    }
}
