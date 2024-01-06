package toyGroupChat.domain.user.sanityCheck;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.user.event.ProfileImageUploadRequested;
import toyGroupChat.domain.user.event.SignUpCompleted;
import toyGroupChat.domain.user.event.UserRemovedByFail;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockProfileImageUploadRequestedReqDto;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockSignUpCompletedReqDto;
import toyGroupChat.domain.user.sanityCheck.reqDtos.MockUserRemovedByFailReqDto;

@Service
@RequiredArgsConstructor
public class UserSanityCheckService {

    // Policy 테스트용으로 ProfileImageUploadRequested 이벤트를 강제로 발생시키기 위해서
    public void mockProfileImageUploadRequested(MockProfileImageUploadRequestedReqDto mockData) {
        (new ProfileImageUploadRequested(mockData)).publish();
    }

    // Policy 테스트용으로 SignUpCompleted 이벤트를 강제로 발생시키기 위해서
    public void mockSignUpCompleted(MockSignUpCompletedReqDto mockData) {
        (new SignUpCompleted(mockData)).publish();
    }

    // Policy 테스트용으로 UserRemovedByFail 이벤트를 강제로 발생시키기 위해서
    public void mockUserRemovedByFail(MockUserRemovedByFailReqDto mockData) {
        (new UserRemovedByFail(mockData)).publish();
    }

}
