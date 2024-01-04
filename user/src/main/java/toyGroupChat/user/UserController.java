package toyGroupChat.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;
import toyGroupChat.user.reqDtos.SignInReqDto;
import toyGroupChat.user.reqDtos.SignUpReqDto;
import toyGroupChat.user.resDtos.SignUpResDto;
import toyGroupChat.user.service.UserService;

import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/signUp")
    public ResponseEntity<SignUpResDto> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signUpReqDto: %s}", signUpReqDto.toString()));
       
        SignUpResDto signUpResDto = new SignUpResDto(this.userService.signUp(signUpReqDto));
       
        CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{signUpResDto: %s}", signUpResDto.toString()));
        return ResponseEntity.ok(signUpResDto);
    }

    @PutMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInReqDto signInReqDtoForToken) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signInReqDtoForToken: %s}", signInReqDtoForToken.toString()));

        String jwtToken = this.userService.tokenBySignIn(signInReqDtoForToken);
        
        CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{jwtToken: %s}", jwtToken));
        return ResponseEntity.ok()
          .header(HttpHeaders.AUTHORIZATION, jwtToken)
          .build();
    }
}
