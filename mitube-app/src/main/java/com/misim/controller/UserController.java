package com.misim.controller;

import com.misim.dto.UserDto;
import com.misim.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute("userDto") @Valid UserDto userDto) {
        // 유저 정보 등록
        userService.registerUser(userDto);

        return "redirect:/user/register?success";
    }
    private boolean emailExist(String email) {

        return userService.findUserByEmail(email) != null;
    }
    private boolean nicknameExist(String nickname) {

        return userService.findUserByNickname(nickname) != null;
    }

    // 닉네임 중복 확인
    @PostMapping("/user/register/nickname")
    public ResponseEntity<Boolean> verifyNickname(@RequestParam(name = "nickname", required = true) String nickname) {

        boolean result = false;

        if (nicknameExist(nickname)) {
            result = true;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // 이메일 본인 인증
    @PostMapping("/user/auth/email")
    public void authEmail(@RequestParam(name = "email", required = true) String email) {
        if (emailExist(email)) {

        } else {

        }
    }

    // 약관
    @GetMapping("/user/register/term")
    public String showTermForm() {
        return "term";
    }

    @PostMapping("/user/register/term")
    public void saveTerm() {

    }
}
