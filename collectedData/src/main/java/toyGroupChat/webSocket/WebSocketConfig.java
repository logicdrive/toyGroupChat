package toyGroupChat.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

import toyGroupChat.webSocket.sanityCheck.SanityCheckSocketHandler;

// WebSocket에 관련 경로들을 등록시키기 위해서
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SanityCheckSocketHandler sanityCheckSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sanityCheckSocketHandler, "/socket/sanityCheck").setAllowedOrigins("*");
    }
}