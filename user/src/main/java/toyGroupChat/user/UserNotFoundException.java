package toyGroupChat.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import toyGroupChat._global.customExceptionControl.CustomException;

@Getter
public class UserNotFoundException extends CustomException {       
    public UserNotFoundException() {
        super(
            HttpStatus.BAD_REQUEST,
            "UserNotFoundException",
            "해당하는 정보의 사용자를 찾을 수 없습니다.\n" +
            "유효한 이메일, 비밀번호인지 확인하거나 계정이 없을 경우 회원가입을 해주세요."
        );
    }
}
