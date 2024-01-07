package toyGroupChat.webSocket.subscribeMessageCreated;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SubscribeMessageCreatedReqDto {
    Long roomId;

    public SubscribeMessageCreatedReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.roomId = jsonObject.getLong("roomId");
    }
}
