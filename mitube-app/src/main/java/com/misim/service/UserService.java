package com.misim.service;

import com.misim.dto.UserDto;
import com.misim.entity.User;
import com.misim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {

        // userDto -> user로 변환하여 db에 저장 (비밀번호 암호화)
        userRepository.save(User.builder()
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build());
    }

    public boolean isDuplicatedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isDuplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public User findUserByNickname(String nickname) {

        return userRepository.findByNickname(nickname);
    }
}
