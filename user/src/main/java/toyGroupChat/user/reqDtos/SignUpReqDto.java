package toyGroupChat.user.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpReqDto {
    private String email;
    private String password;
    private String name;
}
