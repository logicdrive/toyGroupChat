package toyGroupChat.domain.file.sanityCheck;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.file.event.FileUploadFailed;
import toyGroupChat.domain.file.event.FileUploaded;
import toyGroupChat.domain.file.event.ProfileImageUploadFailed;
import toyGroupChat.domain.file.event.ProfileImageUploaded;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockFileUploadFailedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockFileUploadedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadFailedReqDto;
import toyGroupChat.domain.file.sanityCheck.reqDtos.MockProfileImageUploadedReqDto;

@Service
@RequiredArgsConstructor
public class FileSanityCheckService {

    // Policy 테스트용으로 ProfileImageUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockProfileImageUploaded(MockProfileImageUploadedReqDto mockData) {
        (new ProfileImageUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 ProfileImageUploadFailed 이벤트를 강제로 발생시키기 위해서
    public void mockProfileImageUploadFailed(MockProfileImageUploadFailedReqDto mockData) {
        (new ProfileImageUploadFailed(mockData)).publish();
    }


    // Policy 테스트용으로 FileUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockFileUploaded(MockFileUploadedReqDto mockData) {
        (new FileUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 FileUploadFailed 이벤트를 강제로 발생시키기 위해서
    public void mockFileUploadFailed(MockFileUploadFailedReqDto mockData) {
        (new FileUploadFailed(mockData)).publish();
    }    
}
