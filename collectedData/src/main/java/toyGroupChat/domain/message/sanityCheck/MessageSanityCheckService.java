package toyGroupChat.domain.message.sanityCheck;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.message.event.FileUploadRequested;
import toyGroupChat.domain.message.event.MessageCreated;
import toyGroupChat.domain.message.event.MessageRemovedByFail;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockFileUploadRequestedReqDto;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageCreatedReqDto;
import toyGroupChat.domain.message.sanityCheck.reqDtos.MockMessageRemovedByFailReqDto;

@Service
@RequiredArgsConstructor
public class MessageSanityCheckService {

    // Policy 테스트용으로 FileUploadRequested 이벤트를 강제로 발생시키기 위해서
    public void mockFileUploadRequested(MockFileUploadRequestedReqDto mockData) {
        (new FileUploadRequested(mockData)).publish();
    }

    // Policy 테스트용으로 MessageCreated 이벤트를 강제로 발생시키기 위해서
    public void mockMessageCreated(MockMessageCreatedReqDto mockData) {
        (new MessageCreated(mockData)).publish();
    }

    // Policy 테스트용으로 MessageRemovedByFail 이벤트를 강제로 발생시키기 위해서
    public void mockMessageRemovedByFail(MockMessageRemovedByFailReqDto mockData) {
        (new MessageRemovedByFail(mockData)).publish();
    }
}
