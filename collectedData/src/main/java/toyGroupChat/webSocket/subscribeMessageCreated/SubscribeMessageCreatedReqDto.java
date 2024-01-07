package toyGroupChat.webSocket.subscribeMessageCreated;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SubscribeMessageCreatedReqDto {
    Long messageId;

    public SubscribeMessageCreatedReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.messageId = jsonObject.getLong("messageId");
    }
}
