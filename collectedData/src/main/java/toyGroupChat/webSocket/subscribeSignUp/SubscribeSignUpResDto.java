package toyGroupChat.webSocket.subscribeSignUp;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.ToString;

@ToString
public class SubscribeSignUpResDto {
    Long userId;
    String userStatus;

    public SubscribeSignUpResDto(Long userId, String userStatus) {
        this.userId = userId;
        this.userStatus = userStatus;
    }

    public TextMessage jsonTextMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", this.userId);
        jsonObject.put("userStatus", this.userStatus);
        return new TextMessage(jsonObject.toString());
    }
}
