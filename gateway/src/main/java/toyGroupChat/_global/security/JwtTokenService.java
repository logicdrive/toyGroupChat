package toyGroupChat._global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    // 현재 유저가 전송한 JWT에 대한 정보가 담긴 객체를 얻기 위해서
    public JwtDto jwtDto() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null) throw new Exception("JWT 토큰에서 권한 추출에 실패했습니다.");
    
            Object principalObject = authentication.getPrincipal();
            if(!(principalObject instanceof Jwt)) throw new Exception("Jwt 변환에 실패했습니다.");

            return new JwtDto((Jwt)principalObject);
            
        } catch(Exception ex) {
            throw new JwtTokenProcessException(ex.getMessage());
        }
    }
}
