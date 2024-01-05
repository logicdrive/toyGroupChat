package toyGroupChat.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.sanityCheck.reqDtos.LogsReqDto;
import toyGroupChat.sanityCheck.resDtos.LogsResDto;
import toyGroupChat.sanityCheck.resDtos.AuthenticationCheckResDto;

import toyGroupChat.sanityCheck.exceptions.DivByZeroException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final SanityCheckService sanityCheckService;
    private boolean isNormalSanityCheck = true;

    // 정상적인 통신 여부를 단순하게 확인해보기 위해서
    @GetMapping
    public ResponseEntity<Void> sanityCheck() {
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT, "",
            String.format("{isNormalSanityCheck: %b}", this.isNormalSanityCheck));
        
        return ResponseEntity.status((this.isNormalSanityCheck ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR))
            .build();
    }

    // 에러 복구 여부 테스트를 위해서 SanityCheck시에 무조건 정상 처리가 반환되도록 만듬
    @PutMapping("/setNormal")
    public void sanityCheckSetNormal() {
        this.isNormalSanityCheck = true;
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT);
        return;
    }

    // 에러 복구 여부 테스트를 위해서 SanityCheck시에 무조건 내부 서버 에러가 반환되도록 만듬
    @PutMapping("/setError")
    public void sanityCheckSetError() {
        this.isNormalSanityCheck = false;
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
            throw new DivByZeroException();
        }    
    }

    
    // 게이트웨이 JWT 인증시에 관련 정보를 얻을 수 있는지 테스트해보기 위해서
    @GetMapping("/authenticationCheck")
    public ResponseEntity<AuthenticationCheckResDto> authenticationCheck(@RequestHeader("User-Id") Long userId) {
        AuthenticationCheckResDto authenticationCheckResDto = new AuthenticationCheckResDto(userId);
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT, "", String.format("{authenticationCheckResDto: %s}", authenticationCheckResDto.toString()));
        return ResponseEntity.ok(authenticationCheckResDto);
    }
}
