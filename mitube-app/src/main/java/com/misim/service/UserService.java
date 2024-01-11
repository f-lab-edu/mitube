package com.misim.service;

import com.misim.dto.UserDto;
import com.misim.entity.User;
import com.misim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(UserDto userDto) {

        // 비밀번호 암호화


        // userDto -> user로 변환하여 db에 저장
        userRepository.save(User.builder()
                .nickname(userDto.getNickname())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build());
    }

    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public User findUserByNickname(String nickname) {

        return userRepository.findByNickname(nickname);
    }
}
