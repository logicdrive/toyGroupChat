package toyGroupChat.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import toyGroupChat.UserApplication;
import toyGroupChat._global.event.ProfileImageUploadFailed;
import toyGroupChat._global.event.ProfileImageUploaded;
import toyGroupChat._global.event.SignUpCompleted;
import toyGroupChat._global.event.UserRemovedByFail;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "App_User")
public class User {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    private Long profileImageFileId;


    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    public String toString() { 
        return String.format("%s(id=%s, name=%s, email=%s, profileImageFileId=%d)",
            this.getClass().getSimpleName(), this.id, this.name, this.email, this.profileImageFileId);
    }


    @PrePersist
    public void onPrePersist() {
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


    // 프로필 이미지가 S3에 업로드되었을 경우, 관련 정보를 업데이트하기 위해서
    public static void updateProfileImageFileId(ProfileImageUploaded profileImageUploaded) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search User by using JPA", String.format("{profileImageUploaded: %s}", profileImageUploaded.toString()));
        repository().findById(profileImageUploaded.getUserId()).ifPresent(user->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "User is searched by using JPA", String.format("{user: %s}", user.toString()));

            user.setProfileImageFileId(profileImageUploaded.getId());
            repository().save(user);
            
            SignUpCompleted signUpCompleted = new SignUpCompleted(user);
            signUpCompleted.publishAfterCommit();

        });
    }

    // 프로필 이미지 업로드 실패시에 관련 정보를 삭제시키기 위해서
    public static void removeUserByFail(ProfileImageUploadFailed profileImageUploadFailed) {
         CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search User by using JPA", String.format("{profileImageUploadFailed: %s}", profileImageUploadFailed.toString()));
         repository().findById(profileImageUploadFailed.getUserId()).ifPresent(user->{

            repository().delete(user);

            UserRemovedByFail userRemovedByFail = new UserRemovedByFail(user);
            userRemovedByFail.publishAfterCommit();

        });
    }
}
