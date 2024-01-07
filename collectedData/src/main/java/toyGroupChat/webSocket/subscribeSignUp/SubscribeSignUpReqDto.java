package toyGroupChat.webSocket.subscribeSignUp;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SubscribeSignUpReqDto {
    Long userId;

    public SubscribeSignUpReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.userId = jsonObject.getLong("userId");
    }
}
