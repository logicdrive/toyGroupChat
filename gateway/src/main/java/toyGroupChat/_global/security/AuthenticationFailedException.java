package toyGroupChat._global.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import toyGroupChat._global.customExceptionControl.CustomException;

@Getter
public class AuthenticationFailedException extends CustomException {
    public AuthenticationFailedException(String extraMessage) {
        super(
            HttpStatus.BAD_REQUEST,
            "AuthenticationFailedException",
            "유저를 인증하는 과정에서 오류가 발생했습니다.\n" +
            (extraMessage + "\n") +
            "유효한 이메일, 비밀번호인지 확인하거나 계정이 없을 경우 회원가입을 해주세요."
        );
    }
}
