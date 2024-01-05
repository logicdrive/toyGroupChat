package toyGroupChat.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.user.reqDtos.SignInReqDto;
import toyGroupChat.user.reqDtos.SignUpReqDto;
import toyGroupChat.user.resDtos.SignUpResDto;
import toyGroupChat.user.service.UserService;


@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/signUp")
    public ResponseEntity<SignUpResDto> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signUpReqDto: %s}", signUpReqDto.toString()));
        
            SignUpResDto signUpResDto = new SignUpResDto(this.userService.signUp(signUpReqDto));
        
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{signUpResDto: %s}", signUpResDto.toString()));
            return ResponseEntity.ok(signUpResDto);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{signUpReqDto: %s}", signUpReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInReqDto signInReqDtoForToken) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{signInReqDtoForToken: %s}", signInReqDtoForToken.toString()));

            String jwtToken = this.userService.tokenBySignIn(signInReqDtoForToken);
            
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{jwtToken: %s}", jwtToken));
            return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, jwtToken)
            .build();

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{signInReqDtoForToken: %s}", signInReqDtoForToken.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
