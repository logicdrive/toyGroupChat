package toyGroupChat.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.sanityCheck.reqDtos.LogsReqDto;
import toyGroupChat.sanityCheck.resDtos.LogsResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final SanityCheckService sanityCheckService;

    // 정상적인 통신 여부를 단순하게 확인해보기 위해서
    @GetMapping
    public void sanityCheck() {
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT);
        return;
    }

    // 현재 저장된 로그들 중에서 일부분을 간편하게 가져오기 위해서
    @GetMapping("/logs")
    public ResponseEntity<LogsResDto> logs(@ModelAttribute LogsReqDto logsReqDto) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER);

            LogsResDto logsResDto = this.sanityCheckService.logs(logsReqDto);

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{logsSize: %d}", logsResDto.getLogs().size()));
            return ResponseEntity.ok(logsResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{logsReqDto: %s}", logsReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 정상적인 에러 로그 출력 여부를 테스트해보기 위해서
    @GetMapping("/divByZeroCheck")
    public ResponseEntity<Integer> divByZeroCheck() {
        try {
            Integer returnNum = 1/0;
            return ResponseEntity.ok(returnNum);
        } catch(Exception e) {
            CustomLogger.error(e, "Div By Zero Check Message", String.format("{returnNum: %s}", "Undefined"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }    
    }
}
