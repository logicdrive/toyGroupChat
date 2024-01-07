package toyGroupChat.domain.user;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.config.kafka.KafkaProcessor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.user.event.ProfileImageUploadRequested;
import toyGroupChat.domain.user.event.SignUpCompleted;
import toyGroupChat.domain.user.event.UserRemovedByFail;
import toyGroupChat.domain.user.exceptions.UserNotFoundException;
import toyGroupChat.webSocket.subscribeSignUp.SubscribeSignUpSocketHandler;

@Service
@RequiredArgsConstructor
public class UserViewHandler {
    private final UserRepository userRepository;
    private final SubscribeSignUpSocketHandler subscribeSignUpSocketHandler;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ProfileImageUploadRequested'"
    )
    public void whenProfileImageUploadRequested_then_CREATE_1(
        @Payload ProfileImageUploadRequested profileImageUploadRequested
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{profileImageUploadRequested: %s}", profileImageUploadRequested.toString()));
            if (!profileImageUploadRequested.validate()) return;

            User savedUser = this.userRepository.save(
                User.builder()
                    .userId(profileImageUploadRequested.getId())
                    .status("ProfileImageUploadRequested")
                    .build()
            );

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{savedUser: %s}", savedUser.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{profileImageUploadRequested: %s}", profileImageUploadRequested.toString()));
        }
    }


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignUpCompleted'"
    )
    public void whenSignUpCompleted_then_UPDATE_1(
        @Payload SignUpCompleted signUpCompleted
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signUpCompleted: %s}", signUpCompleted.toString()));
            if (!signUpCompleted.validate()) return;


            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search User", String.format("{userId: %s}", signUpCompleted.getId()));            
            User userToUpdate = this.userRepository.findByUserId(signUpCompleted.getId())
                .orElseThrow(() -> new UserNotFoundException());
            
            userToUpdate.setName(signUpCompleted.getName());
            userToUpdate.setEmail(signUpCompleted.getEmail());
            userToUpdate.setProfileImageFileId(signUpCompleted.getProfileImageFileId());
            userToUpdate.setStatus("SignUpCompleted");
            User updatedUser = this.userRepository.save(userToUpdate);


            this.subscribeSignUpSocketHandler.notifyUserUpdate(updatedUser);
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{updatedUser: %s}", updatedUser.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{signUpCompleted: %s}", signUpCompleted.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='UserRemovedByFail'"
    )
    public void whenUserRemovedByFail_then_UPDATE_2(
        @Payload UserRemovedByFail userRemovedByFail
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{userRemovedByFail: %s}", userRemovedByFail.toString()));
            if (!userRemovedByFail.validate()) return;


            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search User", String.format("{userId: %s}", userRemovedByFail.getId()));            
            User userToUpdate = this.userRepository.findByUserId(userRemovedByFail.getId())
                .orElseThrow(() -> new UserNotFoundException());
            
            userToUpdate.setStatus("UserRemovedByFail");
            User updatedUser = this.userRepository.save(userToUpdate);


            this.subscribeSignUpSocketHandler.notifyUserUpdate(updatedUser);
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{updatedUser: %s}", updatedUser.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{userRemovedByFail: %s}", userRemovedByFail.toString()));
        }
    }
}
