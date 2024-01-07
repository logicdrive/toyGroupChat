package toyGroupChat.domain.message.exceptions;

import org.springframework.http.HttpStatus;

import toyGroupChat._global.customExceptionControl.CustomException;

import lombok.Getter;

@Getter
public class MessageNotFoundException extends CustomException {
    public MessageNotFoundException() {
        super(
            HttpStatus.BAD_REQUEST,
            "MessageNotFoundException",
            "주어진 쿼리와 매칭되는 Message를 발견하지 못했습니다. 주어진 쿼리가 정확한지 확인해주세요."
        );
    }
}
