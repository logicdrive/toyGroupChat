package toyGroupChat._global.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import toyGroupChat._global.customExceptionControl.CustomException;

@Getter
public class JwtTokenProcessException extends CustomException {
    public JwtTokenProcessException(String extraMessage) {
        super(
            HttpStatus.BAD_REQUEST,
            "JwtTokenProcessException",
            "JWT 토큰에서 유저 정보를 추출하는 과정에서 오류가 발생했습니다.\n" +
            (extraMessage + "\n") +
            "서버 내부의 오류일 수 있습니다. 잠시후 다시 시도해보거나 지속적인 오류 발생시 관리자에게 문의하세요."
        );
    }
}
