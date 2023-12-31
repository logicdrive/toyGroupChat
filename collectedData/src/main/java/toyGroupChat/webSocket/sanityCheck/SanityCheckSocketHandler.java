package toyGroupChat.webSocket.sanityCheck;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

// Sanity Check를 위한 단순한 Echo 서비스를 구현하기 위해서
@Component
public class SanityCheckSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            SanityCheckReqDto sanityCheckReqDto = new SanityCheckReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{sanityCheckReqDto: %s}", sanityCheckReqDto.toString()));

            SanityCheckResDto sanityCheckResDto = new SanityCheckResDto(sanityCheckReqDto.getMessage());
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{sanityCheckResDto: %s}", sanityCheckResDto.toString()));
            
            session.sendMessage(sanityCheckResDto.jsonTextMessage());

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }
    
}
