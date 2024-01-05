package toyGroupChat.domain;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.event.FileUploadRequested;
import toyGroupChat._global.event.ProfileImageUploadRequested;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 프로필 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploadRequested'"
    )
    public void wheneverProfileImageUploadRequested_RequestProfileImageUpload(
        @Payload ProfileImageUploadRequested profileImageUploadRequested
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploadRequested: %s}", profileImageUploadRequested.toString()));
            File.requestProfileImageUpload(profileImageUploadRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploadRequested: %s}", profileImageUploadRequested.toString()));
        }
    }

    // 메세지 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploadRequested'"
    )
    public void wheneverFileUploadRequested_RequestFileUpload(
        @Payload FileUploadRequested fileUploadRequested
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploadRequested: %s}", fileUploadRequested.toString()));
            File.requestFileUpload(fileUploadRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploadRequested: %s}", fileUploadRequested.toString()));
        }
    }
}
