package toyGroupChat.room.exceptions;

import org.springframework.http.HttpStatus;

import toyGroupChat._global.customExceptionControl.CustomException;

import lombok.Getter;

@Getter
public class RoomNotFoundException extends CustomException {
    public RoomNotFoundException() {
        super(
            HttpStatus.BAD_REQUEST,
            "RoomNotFoundException",
            "주어진 SharedCode와 매칭되는 Room을 발견하지 못했습니다. URL이 정확한지 다시 한번 확인해주세요."
        );
    }
}
