package toyGroupChat.domain;

import toyGroupChat.MessageApplication;
import toyGroupChat._global.event.FileUploadFailed;
import toyGroupChat._global.event.FileUploaded;
import toyGroupChat._global.event.MessageCreated;
import toyGroupChat._global.event.MessageRemovedByFail;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PostRemove;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "App_Message")
public class Message {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long roomId;

    private Long userId;

    private String content;

    private Long fileId;

    private Date createdDate;

    public static MessageRepository repository() {
        MessageRepository fileRepository = MessageApplication.applicationContext.getBean(
            MessageRepository.class
        );
        return fileRepository;
    }


    @PrePersist
    public void onPrePersist() {
        this.createdDate = new Date();

        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("Try to create %s by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("%s is created by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }


    @PreUpdate
    public void onPreUpdate() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("Try to update %s by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }

    @PostUpdate
    public void onPostUpdate() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("%s is updated by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }


    @PreRemove
    public void onPreRemove() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT, 
            String.format("Try to delete %s by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }

    @PostRemove
    public void onPostRemove() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("%s is deleted by using JPA", this.getClass().getSimpleName()),
            String.format("{%s: %s}", this.getClass().getSimpleName(), this.toString())
        );
    }


    // 메세지 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    public static void updateFileId(FileUploaded fileUploaded) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Message by using JPA", String.format("{fileUploaded: %s}", fileUploaded.toString()));
        repository().findById(fileUploaded.getMessageId()).ifPresent(message->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Message is searched by using JPA", String.format("{message: %s}", message.toString()));

            message.setFileId(fileUploaded.getId());
            Message updatedMessage = repository().save(message);
            
            MessageCreated messageCreated = new MessageCreated(updatedMessage);
            messageCreated.publishAfterCommit();

        });
    }

    // 메세지 파일 업로드 실패시에 관련 정보를 삭제시키기 위해서
    public static void removeMessageByFail(FileUploadFailed fileUploadFailed) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Message by using JPA", String.format("{fileUploadFailed: %s}", fileUploadFailed.toString()));
        repository().findById(fileUploadFailed.getMessageId()).ifPresent(message->{

           repository().delete(message);

           MessageRemovedByFail messageRemovedByFail = new MessageRemovedByFail(message);
           messageRemovedByFail.publishAfterCommit();

       });
   }
}
