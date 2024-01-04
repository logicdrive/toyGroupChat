package toyGroupChat._global.security;

import static java.util.stream.Collectors.joining;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.user.reqDtos.SignInReqDto;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final AuthenticationManager authenticationManager; // Spring Security를 활용한 인증을 위해서
    private final JwtEncoder jwtEncoder; // JWT의 간편한 생성을 위한 외부라이브러리
    private @Value("${jwt.issuer}") String jwtConfigIssuer;
    private @Value("${jwt.expire-after-seconds}") Long jwtConfigExpireAfterSeconds;

    public String tokenValue(SignInReqDto signInReqDto) {
        try {
            // Spring Security를 활용한 인증을 수행하기 위해서
            // 인증 실패시 자동으로 예외가 발생됨
            Authentication authentication =
                this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInReqDto.getEmail(), signInReqDto.getPassword()));
            
            ServiceUserDetail serviceUserDetail = ((ServiceUserDetail) authentication.getPrincipal());
            JwtEncoderParameters jwtParameters = JwtEncoderParameters.from(
                JwtClaimsSet.builder()
                    .issuer(this.jwtConfigIssuer)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(this.jwtConfigExpireAfterSeconds))
                    .subject(serviceUserDetail.getEmail())
                    .claim("name", serviceUserDetail.getName())
                    .claim("roles", authentication.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(joining(" ")))
                    .build()
            );
            return this.jwtEncoder.encode(jwtParameters).getTokenValue();

        } catch (AuthenticationException ex) {
            throw new AuthenticationFailedException(ex.getMessage());
        }
    }
}
