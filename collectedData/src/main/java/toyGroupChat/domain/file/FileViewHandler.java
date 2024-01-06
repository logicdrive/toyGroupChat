package toyGroupChat.domain.file;

import java.util.Optional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.domain.file.event.FileUploadFailed;
import toyGroupChat.domain.file.event.FileUploaded;
import toyGroupChat.domain.file.event.ProfileImageUploadFailed;
import toyGroupChat.domain.file.event.ProfileImageUploaded;

@Service
@RequiredArgsConstructor
public class FileViewHandler {
    private final FileRepository fileRepository;

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploaded'"
    )
    public void whenProfileImageUploaded_then_CREATE_1(
        @Payload ProfileImageUploaded profileImageUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploaded: %s}", profileImageUploaded.toString()));
            if (!profileImageUploaded.validate()) return;

            File savedFile = this.fileRepository.save(
                File.builder()
                    .fileId(profileImageUploaded.getId())
                    .name(profileImageUploaded.getName())
                    .url(profileImageUploaded.getUrl())
                    .createdDate(profileImageUploaded.getCreatedDate())
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedFile: %s}", savedFile.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploaded: %s}", profileImageUploaded.toString()));
        }
    }

   @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploaded'"
    )
    public void whenFileUploaded_then_CREATE_2(
        @Payload FileUploaded fileUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploaded: %s}", fileUploaded.toString()));
            if (!fileUploaded.validate()) return;

            File savedFile = this.fileRepository.save(
                File.builder()
                    .fileId(fileUploaded.getId())
                    .name(fileUploaded.getName())
                    .url(fileUploaded.getUrl())
                    .createdDate(fileUploaded.getCreatedDate())
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedFile: %s}", savedFile.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploaded: %s}", fileUploaded.toString()));
        }
    }


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploadFailed'"
    )
    public void whenProfileImageUploadFailed_then_DELETE_1(
        @Payload ProfileImageUploadFailed profileImageUploadFailed
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploadFailed: %s}", profileImageUploadFailed.toString()));
            if (!profileImageUploadFailed.validate()) return;

            Optional<File> optionalFile = this.fileRepository.findByFileId(profileImageUploadFailed.getId());
            if(!(optionalFile.isPresent())) return;

            this.fileRepository.delete(optionalFile.get());
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{deletedFile: %s}", optionalFile.get().toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploadFailed: %s}", profileImageUploadFailed.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FileUploadFailed'"
    )
    public void whenFileUploadFailed_then_DELETE_2(
        @Payload FileUploadFailed fileUploadFailed
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{fileUploadFailed: %s}", fileUploadFailed.toString()));
            if (!fileUploadFailed.validate()) return;

            Optional<File> optionalFile = this.fileRepository.findByFileId(fileUploadFailed.getId());
            if(!(optionalFile.isPresent())) return;

            this.fileRepository.delete(optionalFile.get());
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{deletedFile: %s}", optionalFile.get().toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{fileUploadFailed: %s}", fileUploadFailed.toString()));
        }
    }
}
