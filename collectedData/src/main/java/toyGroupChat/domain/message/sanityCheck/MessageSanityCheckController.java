package toyGroupChat.domain.message.sanityCheck;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.message.sanityCheck.reqDtos.MockFileUploadRequestedReqDto;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageCreatedReqDto;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageRemovedByFailReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages/sanityCheck")
public class MessageSanityCheckController {
    private final MessageSanityCheckService messageSanityCheckService;

    // Policy 테스트용으로 FileUploadRequested 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/FileUploadRequested")
    public void mockFileUploadRequested(@RequestBody MockFileUploadRequestedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.messageSanityCheckService.mockFileUploadRequested(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 MessageCreated 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/MessageCreated")
    public void mockMessageCreated(@RequestBody MockMessageCreatedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.messageSanityCheckService.mockMessageCreated(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 MessageRemovedByFail 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/MessageRemovedByFail")
    public void mockMessageRemovedByFail(@RequestBody MockMessageRemovedByFailReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.messageSanityCheckService.mockMessageRemovedByFail(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
