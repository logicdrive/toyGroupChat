package toyGroupChat.sanityCheck.exceptions;

import org.springframework.http.HttpStatus;

import toyGroupChat._global.customExceptionControl.CustomException;

import lombok.Getter;

@Getter
public class DivByZeroException extends CustomException {
    public DivByZeroException() {
        super(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "SanityCheck_DivByZeroException",
            "0으로 나눠서 예외가 발생했습니다.(SanityCheck 용도)"
        );
    }
}
