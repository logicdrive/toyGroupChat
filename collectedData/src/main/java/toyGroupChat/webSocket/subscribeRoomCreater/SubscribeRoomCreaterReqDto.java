package toyGroupChat.webSocket.subscribeRoomCreater;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SubscribeRoomCreaterReqDto {
    Long roomId;

    public SubscribeRoomCreaterReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.roomId = jsonObject.getLong("roomId");
    }
}
