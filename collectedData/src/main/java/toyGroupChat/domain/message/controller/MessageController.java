package toyGroupChat.domain.message.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.message.controller.reqDtos.MessageWithCheckReqDto;
import toyGroupChat.domain.message.controller.reqDtos.MessagesWithCheckReqDto;
import toyGroupChat.domain.message.controller.resDtos.MessageWithCheckResDto;
import toyGroupChat.domain.message.controller.resDtos.MessagesWithCheckResDto;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    // 그룹 채팅 방 내부의 모든 메세지를 조회 횟수와 함께 받기 위해서
    @GetMapping("/messagesWithCheck")
    public ResponseEntity<MessagesWithCheckResDto> messagesWithCheck(@ModelAttribute MessagesWithCheckReqDto messagesWithCheckReqDto, @RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{messagesWithCheckReqDto: %s}", messagesWithCheckReqDto.toString()));
        
            MessagesWithCheckResDto messagesWithCheckResDto = this.messageService.messagesWithCheck(messagesWithCheckReqDto, userId);
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{messagesWithCheckResDto: %s}", messagesWithCheckResDto.toString()));
            return ResponseEntity.ok(messagesWithCheckResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{messagesWithCheckReqDto: %s}", messagesWithCheckReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 조회 횟수와 함께 메세지를 받기 위해서
    @GetMapping("/messageWithCheck")
    public ResponseEntity<MessageWithCheckResDto> messageWithCheck(@ModelAttribute MessageWithCheckReqDto messageWithCheckReqDto, @RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{messageWithCheckReqDto: %s}", messageWithCheckReqDto.toString()));
        
            MessageWithCheckResDto messageWithCheckResDto = this.messageService.messageWithCheck(messageWithCheckReqDto, userId);
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{messageWithCheckResDto: %s}", messageWithCheckResDto.toString()));
            return ResponseEntity.ok(messageWithCheckResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{messageWithCheckReqDto: %s}", messageWithCheckReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
