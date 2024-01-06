package toyGroupChat.domain.room.sanityCheck;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomCreatedReqDto;
import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomCreaterAddedReqDto;
import toyGroupChat.domain.room.sanityCheck.reqDtos.MockRoomUserAddedReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms/sanityCheck")
public class RoomSanityCheckController {
    private final RoomSanityCheckService roomSanityCheckService;

    // Policy 테스트용으로 RoomCreated 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/RoomCreated")
    public void mockRoomCreated(@RequestBody MockRoomCreatedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.roomSanityCheckService.mockRoomCreated(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 RoomCreaterAdded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/RoomCreaterAdded")
    public void mockRoomCreaterAdded(@RequestBody MockRoomCreaterAddedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.roomSanityCheckService.mockRoomCreaterAdded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 RoomUserAdded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/RoomUserAdded")
    public void mockRoomUserAdded(@RequestBody MockRoomUserAddedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.roomSanityCheckService.mockRoomUserAdded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
