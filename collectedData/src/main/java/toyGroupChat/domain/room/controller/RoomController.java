package toyGroupChat.domain.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.room.controller.resDtos.JoinedRoomsResDto;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    // 내가 속한 그룹 채팅 방에 대한 정보들을 반환시키기 위해서
    @GetMapping("/joinedRooms")
    public ResponseEntity<JoinedRoomsResDto> joinedRooms(@RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{userId: %d}", userId));
        
            JoinedRoomsResDto joinedRoomsResDto = new JoinedRoomsResDto(this.roomService.joinedRooms(userId));
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{joinedRoomsResDto: %s}", joinedRoomsResDto.toString()));
            return ResponseEntity.ok(joinedRoomsResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{userId: %d}", userId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
