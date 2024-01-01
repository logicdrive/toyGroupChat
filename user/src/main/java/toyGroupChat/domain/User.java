package toyGroupChat.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

import toyGroupChat.UserApplication;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

@Entity
@Table(name = "App_User")
@Data
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


    @PrePersist
    public void onPrePersist() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("Try to create %s by using JPA", this.getClass().getSimpleName()),
            String.format("{video: %s}", this.toString())
        );
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("%s is created by using JPA", this.getClass().getSimpleName()),
            String.format("{video: %s}", this.toString())
        );
    }


    @PreUpdate
    public void onPreUpdate() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("Try to update %s by using JPA", this.getClass().getSimpleName()),
            String.format("{video: %s}", this.toString())
        );
    }

    @PostUpdate
    public void onPostUpdate() {
        CustomLogger.debug(
            CustomLoggerType.EFFECT,
            String.format("%s is updated by using JPA", this.getClass().getSimpleName()),
            String.format("{video: %s}", this.toString())
        );
    }
}
