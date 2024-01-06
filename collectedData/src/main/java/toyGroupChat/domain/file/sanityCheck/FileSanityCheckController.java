package toyGroupChat.domain.file.sanityCheck;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.file.sanityCheck.reqDtos.MockFileUploadFailedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockFileUploadedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadFailedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadedReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files/sanityCheck")
public class FileSanityCheckController {
    private final FileSanityCheckService userSanityCheckService;

    // Policy 테스트용으로 ProfileImageUploaded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/ProfileImageUploaded")
    public void mockProfileImageUploaded(@RequestBody MockProfileImageUploadedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockProfileImageUploaded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 ProfileImageUploadFailed 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/ProfileImageUploadFailed")
    public void mockProfileImageUploadFailed(@RequestBody MockProfileImageUploadFailedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockProfileImageUploadFailed(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }


    // Policy 테스트용으로 FileUploaded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/FileUploaded")
    public void mockFileUploaded(@RequestBody MockFileUploadedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockFileUploaded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 FileUploadFailed 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/FileUploadFailed")
    public void mockFileUploadFailed(@RequestBody MockFileUploadFailedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockFileUploadFailed(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
