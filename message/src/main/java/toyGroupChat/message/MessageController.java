package toyGroupChat.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @PutMapping("/createMessage")
    public ResponseEntity<CreateMessageResDto> createMessage(@RequestBody CreateMessageReqDto createMessageReqDto, @RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{createMessageReqDto: %s}", createMessageReqDto.toString()));
        
            CreateMessageResDto createMessageResDto = new CreateMessageResDto(this.messageService.createMessage(createMessageReqDto, userId));
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{createMessageResDto: %s}", createMessageResDto.toString()));
            return ResponseEntity.ok(createMessageResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{createMessageReqDto: %s}", createMessageReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
