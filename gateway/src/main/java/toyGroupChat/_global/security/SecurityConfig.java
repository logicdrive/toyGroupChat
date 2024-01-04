package toyGroupChat._global.security;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // JWT 유효성 검사에 사용될 RSA 암호화 관련 공개키, 개인키를 특정 파일경로에서 불러오기 위해서
    @Value("${jwt.public.key}")
    private RSAPublicKey rsaPublicKey;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        // 개발용 버전을 위해서 보안 요소를 비활성화시키기 위해서
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())

            .authorizeExchange(request -> request
                .anyExchange().permitAll()
            );
        
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.rsaPublicKey).build();
    }
}