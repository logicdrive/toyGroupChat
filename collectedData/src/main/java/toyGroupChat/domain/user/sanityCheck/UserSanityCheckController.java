package toyGroupChat.domain.user.sanityCheck;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.user.sanityCheck.reqDtos.MockProfileImageUploadRequestedReqDto;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockSignUpCompletedReqDto;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockUserRemovedByFailReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/sanityCheck")
public class UserSanityCheckController {
    private final UserSanityCheckService userSanityCheckService;

    // Policy 테스트용으로 ProfileImageUploadRequested 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/ProfileImageUploadRequested")
    public void mockProfileImageUploadRequested(@RequestBody MockProfileImageUploadRequestedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockProfileImageUploadRequested(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 SignUpCompleted 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/SignUpCompleted")
    public void mockSignUpCompleted(@RequestBody MockSignUpCompletedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockSignUpCompleted(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 UserRemovedByFail 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/UserRemovedByFail")
    public void mockUserRemovedByFail(@RequestBody MockUserRemovedByFailReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.userSanityCheckService.mockUserRemovedByFail(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
