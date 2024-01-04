package toyGroupChat.user.resDtos;

import lombok.Getter;
import lombok.ToString;
import toyGroupChat.domain.User;

@Getter
@ToString
public class SignUpResDto {
    private final String email;
    private final String name;

    public SignUpResDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
