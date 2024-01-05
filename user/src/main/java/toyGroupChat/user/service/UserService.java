package toyGroupChat.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toyGroupChat._global.event.ProfileImageUploadRequested;
import toyGroupChat._global.security.JwtTokenService;
import toyGroupChat.domain.User;
import toyGroupChat.domain.UserRepository;
import toyGroupChat.user.reqDtos.SignInReqDto;
import toyGroupChat.user.reqDtos.SignUpReqDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder; // Spring Security에서 사용하는 PasswordEncoder를 활용하기 위해서

    
    public User signUp(SignUpReqDto signUpReqDto) {
        User savedUser = this.userRepository.save(
                User.builder()
                    .email(signUpReqDto.getEmail())
                    .password(this.passwordEncoder.encode(signUpReqDto.getPassword()))
                    .name(signUpReqDto.getName())
                    .build()
            );
        
        ProfileImageUploadRequested profileImageUploadRequested = new ProfileImageUploadRequested();
        profileImageUploadRequested.setId(savedUser.getId());
        profileImageUploadRequested.setDataUrl(signUpReqDto.getDataUrl());
        profileImageUploadRequested.publishAfterCommit();

        return savedUser;
    }

    public String tokenBySignIn(SignInReqDto signInReqDto) {
        return this.jwtTokenService.tokenValue(signInReqDto);
    }
}
