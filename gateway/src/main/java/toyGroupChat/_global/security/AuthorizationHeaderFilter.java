package toyGroupChat._global.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final Environment environment;

    public AuthorizationHeaderFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }
    
    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        return (exchange, chain) -> {
            System.out.println("my filter");

            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return responseWithError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            System.out.println("authorizationHeader :" + authorizationHeader);
            // String jwt = authorizationHeader.replace("Bearer", "");
            // if (!isJwtValid(jwt)) return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            
            return chain.filter(exchange);
        };
    }

    private Mono<Void> responseWithError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    
    // private boolean isJwtValid(String jwt) {
    //     String subject = null;
    //     try {
    //         subject = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
    //                 .parseClaimsJws(jwt).getBody()
    //                 .getSubject();
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //     }
    //     return !Strings.isBlank(subject);
    // }

    public static class Config {}
}