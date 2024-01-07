package toyGroupChat.webSocket.subscribeMessageCreated;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.ToString;

@ToString
public class SubscribeMessageCreatedResDto {
    Long messageId;
    String messageStatus;

    public SubscribeMessageCreatedResDto(Long messageId, String messageStatus) {
        this.messageId = messageId;
        this.messageStatus = messageStatus;
    }

    public TextMessage jsonTextMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageId", this.messageId);
        jsonObject.put("messageStatus", this.messageStatus);
        return new TextMessage(jsonObject.toString());
    }
}
