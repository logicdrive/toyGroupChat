package toyGroupChat.domain;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.event.ProfileImageUploadFailed;
import toyGroupChat._global.event.ProfileImageUploaded;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 프로필 이미지가 S3에 업로드되었을 경우, 관련 정보를 업데이트하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploaded'"
    )
    public void wheneverProfileImageUploaded_UpdateProfileImageFileId(
        @Payload ProfileImageUploaded profileImageUploaded
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploaded: %s}", profileImageUploaded.toString()));
            User.updateProfileImageFileId(profileImageUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploaded: %s}", profileImageUploaded.toString()));
        }
    }

    // 프로필 이미지 업로드 실패시에 관련 정보를 삭제시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploadFailed'"
    )
    public void wheneverProfileImageUploadFailed_RemoveUserByFail(
        @Payload ProfileImageUploadFailed profileImageUploadFailed
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploadFailed: %s}", profileImageUploadFailed.toString()));
            User.removeUserByFail(profileImageUploadFailed);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploadFailed: %s}", profileImageUploadFailed.toString()));
        }
    }
}
