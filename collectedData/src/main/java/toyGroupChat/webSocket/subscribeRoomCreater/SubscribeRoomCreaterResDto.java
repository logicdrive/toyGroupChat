package toyGroupChat.webSocket.subscribeRoomCreater;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.ToString;

@ToString
public class SubscribeRoomCreaterResDto {
    Long roomId;
    Long userId;

    public SubscribeRoomCreaterResDto(Long roomId, Long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public TextMessage jsonTextMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomId", this.roomId);
        jsonObject.put("userId", this.userId);
        return new TextMessage(jsonObject.toString());
    }
}
