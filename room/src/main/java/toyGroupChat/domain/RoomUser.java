package toyGroupChat.domain;

import toyGroupChat.RoomApplication;
import toyGroupChat._global.event.RoomCreated;
import toyGroupChat._global.event.RoomCreaterAdded;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

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
@Table(name = "App_RoomUser")
public class RoomUser {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long roomId;

    private Long userId;

    public static RoomUserRepository repository() {
        RoomUserRepository roomUserRepository = RoomApplication.applicationContext.getBean(
            RoomUserRepository.class
        );
        return roomUserRepository;
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


    // 그룹 룸 생성시에 생성자를 그룹 룸 유저 목록에 추가시키기 위해서
    public static void addRoomCreater(RoomCreated roomCreated) {
        RoomUser savedRoomUser = repository().save(
            RoomUser.builder()
                .roomId(roomCreated.getId())
                .userId(roomCreated.getCreaterUserId())
                .build()
        );

        RoomCreaterAdded roomCreaterAdded = new RoomCreaterAdded(savedRoomUser);
        roomCreaterAdded.publishAfterCommit();
    }
}
