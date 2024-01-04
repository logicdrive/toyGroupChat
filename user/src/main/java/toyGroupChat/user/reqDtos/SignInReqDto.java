package toyGroupChat.user.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInReqDto {
    private String email;
    private String password;
}
