package com.misim.controller;

import com.misim.dto.UserDto;
import com.misim.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.misim.util.HttpStatusResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;

    // 유저 정보 등록
    @PostMapping("signup")
    public ResponseEntity signupUser(@Valid UserDto userDto, BindingResult result) {

        // @Valid 검증 과정에서 오류가 발견되면, BAD_REQUEST 처리.
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        userService.registerUser(userDto);

        return RESPONSE_OK;
    }

    // 닉네임 중복 확인
    // url 주소에 nickname이 포함되도 괜찮은지 의문이 생긴다.
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
