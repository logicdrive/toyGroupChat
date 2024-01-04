package toyGroupChat.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.User;
import toyGroupChat.domain.UserRepository;
import toyGroupChat.user.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserManageService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException());
    }
}
