package toyGroupChat.room;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.room.reqDtos.AddRoomUserReqDto;
import toyGroupChat.room.reqDtos.CreateRoomReqDto;
import toyGroupChat.room.resDtos.AddRoomUserResDto;
import toyGroupChat.room.resDtos.CreateRoomResDto;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @PutMapping("/createRoom")
    public ResponseEntity<CreateRoomResDto> createRoom(@RequestBody CreateRoomReqDto createRoomReqDto, @RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{createRoomReqDto: %s}", createRoomReqDto.toString()));
        
            CreateRoomResDto createRoomResDto = new CreateRoomResDto(this.roomService.createRoom(createRoomReqDto, userId));
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{createRoomResDto: %s}", createRoomResDto.toString()));
            return ResponseEntity.ok(createRoomResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{createRoomReqDto: %s}", createRoomReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/addRoomUser")
    public ResponseEntity<AddRoomUserResDto> addRoomUser(@RequestBody AddRoomUserReqDto addRoomUserReqDto, @RequestHeader("User-Id") Long userId) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{addRoomUserReqDto: %s}", addRoomUserReqDto.toString()));
        
            AddRoomUserResDto addRoomUserResDto = new AddRoomUserResDto(this.roomService.addRoomUser(addRoomUserReqDto, userId));
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{addRoomUserResDto: %s}", addRoomUserResDto.toString()));
            return ResponseEntity.ok(addRoomUserResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{addRoomUserReqDto: %s}", addRoomUserReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
