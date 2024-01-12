package com.misim.controller;

import com.misim.dto.UserDto;
import com.misim.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.misim.util.HttpStatusResponseEntity.RESPONSE_CONFLICT;
import static com.misim.util.HttpStatusResponseEntity.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;

    // 유저 정보 등록
    @PostMapping("signup")
    public ResponseEntity<HttpStatus> signupUser(@RequestBody @Valid UserDto userDto) {

        userService.registerUser(userDto);

        return RESPONSE_OK;
    }

    // 닉네임 중복 확인
    @PostMapping("signup/{nickname}")
    public ResponseEntity<HttpStatus> checkNickname(@PathVariable String nickname) {

        if (userService.isDuplicatedNickname(nickname)) {
            return RESPONSE_CONFLICT;
        }

        return RESPONSE_OK;
    }

    /*
    // 이메일 본인 인증
    @PostMapping("verify/{email}")
    public ResponseEntity<HttpStatus> verifyEmail(@PathVariable String email) {
        if (userService.isDuplicatedEmail(email)) {

        } else {

        }
    }

    // 약관
    @GetMapping("term")
    public ResponseEntity<> showTerm() {

    }

    @PostMapping("term/{nickname}")
    public ResponseEntity<HttpStatus> saveTerm(@PathVariable String nickname) {

    }

     */
}
