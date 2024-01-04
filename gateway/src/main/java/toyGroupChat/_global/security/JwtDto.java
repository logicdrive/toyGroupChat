package toyGroupChat._global.security;

import lombok.Getter;

import org.springframework.security.oauth2.jwt.Jwt;

// JWT에 담긴 내용들을 간편하게 접근하기 위한 DTO
@Getter
public class JwtDto {
    private String email;
    private String name;

    public JwtDto(Jwt jwt) {
        this.email = jwt.getSubject();
        this.name = jwt.getClaim("name");
    }
}
