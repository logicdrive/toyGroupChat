package toyGroupChat.domain;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.event.FileUploadFailed;
import toyGroupChat._global.event.FileUploaded;
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

    // 메세지 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploaded'"
    )
    public void wheneverFileUploaded_UpdateFileId(
        @Payload FileUploaded fileUploaded
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploaded: %s}", fileUploaded.toString()));
            Message.updateFileId(fileUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploaded: %s}", fileUploaded.toString()));
        }
    }

    // 메세지 파일 업로드 실패시에 관련 정보를 삭제시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploadFailed'"
    )
    public void wheneverFileUploadFailed_RemoveMessageByFail(
        @Payload FileUploadFailed fileUploadFailed
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploadFailed: %s}", fileUploadFailed.toString()));
            Message.removeMessageByFail(fileUploadFailed);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploadFailed: %s}", fileUploadFailed.toString()));
        }
    }
}
