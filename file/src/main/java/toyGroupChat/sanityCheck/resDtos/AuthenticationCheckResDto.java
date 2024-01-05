package toyGroupChat.sanityCheck.resDtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationCheckResDto {
    private final Long userId;

    public AuthenticationCheckResDto(Long userId) {
        this.userId = userId;
    }
}

