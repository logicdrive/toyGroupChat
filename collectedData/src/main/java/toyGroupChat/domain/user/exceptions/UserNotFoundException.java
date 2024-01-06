package toyGroupChat.domain.user.exceptions;

import org.springframework.http.HttpStatus;

import toyGroupChat._global.customExceptionControl.CustomException;

import lombok.Getter;

@Getter
public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(
            HttpStatus.BAD_REQUEST,
            "UserNotFoundException",
            "주어진 데이터와 매칭되는 User를 발견하지 못했습니다."
        );
    }
}
