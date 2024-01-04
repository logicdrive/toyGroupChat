package toyGroupChat._global.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // JWT 유효성 검사에 사용될 RSA 암호화 관련 공개키, 개인키를 특정 파일경로에서 불러오기 위해서
    @Value("${jwt.public.key}")
    private RSAPublicKey rsaPublicKey;
  
    @Value("${jwt.private.key}")
    private RSAPrivateKey rsaPrivateKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 개발용 버전을 위해서 보안 요소를 비활성화시키기 위해서
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())

            // 모든 요청에 대한 접근을 허용하기 위해서
            .authorizeHttpRequests(request -> request
                .anyRequest().permitAll()
            )
            
            // JWT 관련 설정을 추가시키기 위해서
            // JWT 를 쓰므로, 필요없는 세션 관리를 비활성화시키기 위해서
            .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // JWT 인증 실패시에 실패 메세지를 보내기 위해서
            .exceptionHandling(handle -> handle
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // JWT 관리를 위한 노출 함수들
    // AuthenticationManager을 주입을 통해서 사용할 수 있도록 만들기 위해서
    @Bean
    public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

    // RSA 암호화를 이용해서 JWT 토큰의 유효성을 검증하기 위한 인코더 & 디코더
    @Bean
    public JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
