package toyGroupChat.domain;

import toyGroupChat.FileApplication;
import toyGroupChat._global.event.FileUploadFailed;
import toyGroupChat._global.event.FileUploadRequested;
import toyGroupChat._global.event.FileUploaded;
import toyGroupChat._global.event.ProfileImageUploadFailed;
import toyGroupChat._global.event.ProfileImageUploadRequested;
import toyGroupChat._global.event.ProfileImageUploaded;
import toyGroupChat._global.externalSystemProxy.ExternalSystemProxyService;
import toyGroupChat._global.externalSystemProxy.reqDtos.UploadFileReqDto;
import toyGroupChat._global.externalSystemProxy.resDtos.UploadFileResDto;
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
@Table(name = "App_File")
public class File {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String url;

    private Date createdDate;

    public static FileRepository repository() {
        FileRepository fileRepository = FileApplication.applicationContext.getBean(
            FileRepository.class
        );
        return fileRepository;
    }

    public static ExternalSystemProxyService externalSystemProxyService() {
        ExternalSystemProxyService externalSystemProxyService = FileApplication.applicationContext.getBean(
            ExternalSystemProxyService.class
        );
        return externalSystemProxyService;
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


    // 프로필 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    public static void requestProfileImageUpload(ProfileImageUploadRequested profileImageUploadRequested) throws Exception {
        File savedFile = null;
        
        try {

            savedFile = repository().save(
                File.builder()
                    .name("profileImage")
                    .build()
            );

        } catch(Exception e) {
            ProfileImageUploadFailed profileImageUploadFailed = new ProfileImageUploadFailed();
            profileImageUploadFailed.setId(0L);
            profileImageUploadFailed.setUserId(profileImageUploadRequested.getId());
            profileImageUploadFailed.publishAfterCommit();

            throw e;
        }

        try {
            
            UploadFileReqDto uploadFileReqDto = new UploadFileReqDto(profileImageUploadRequested.getDataUrl());
            UploadFileResDto uploadFileResDto = externalSystemProxyService().uploadFile(uploadFileReqDto);
            savedFile.setUrl(uploadFileResDto.getFileUrl());
            File updatedFile = repository().save(savedFile);

            ProfileImageUploaded profileImageUploaded = new ProfileImageUploaded(updatedFile, profileImageUploadRequested.getId());
            profileImageUploaded.publishAfterCommit();

        } catch(Exception e) {
            ProfileImageUploadFailed profileImageUploadFailed = new ProfileImageUploadFailed(savedFile, profileImageUploadRequested.getId());
            profileImageUploadFailed.publishAfterCommit();

            throw e;
        }
    }

    // 메세지 파일 업로드 요칭시에 해당 DataUrl을 기반으로 S3에 업로드하기 위해서
    public static void requestFileUpload(FileUploadRequested fileUploadRequested) throws Exception {
        File savedFile = null;
        
        try {

            savedFile = repository().save(
                File.builder()
                    .name(fileUploadRequested.getName())
                    .build()
            );

        } catch(Exception e) {
            FileUploadFailed fileUploadFailed = new FileUploadFailed();
            fileUploadFailed.setId(0L);
            fileUploadFailed.setMessageId(fileUploadRequested.getId());
            fileUploadFailed.publishAfterCommit();

            throw e;
        }

        try {
            
            UploadFileReqDto uploadFileReqDto = new UploadFileReqDto(fileUploadRequested.getDataUrl());
            UploadFileResDto uploadFileResDto = externalSystemProxyService().uploadFile(uploadFileReqDto);
            savedFile.setUrl(uploadFileResDto.getFileUrl());
            File updatedFile = repository().save(savedFile);

            FileUploaded fileUploaded = new FileUploaded(updatedFile, fileUploadRequested.getId());
            fileUploaded.publishAfterCommit();

        } catch(Exception e) {
            FileUploadFailed fileUploadFailed = new FileUploadFailed(savedFile, fileUploadRequested.getId());
            fileUploadFailed.publishAfterCommit();

            throw e;
        }
    }
}
