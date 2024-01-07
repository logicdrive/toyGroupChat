package toyGroupChat.webSocket.signUpSubscribe;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SignUpSubscribeReqDto {
    Long userId;

    public SignUpSubscribeReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.userId = jsonObject.getLong("userId");
    }
}
