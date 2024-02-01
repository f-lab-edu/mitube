package com.misim.controller;

import com.misim.controller.model.SmsVerificationDto;
import com.misim.controller.model.UserDto;
import com.misim.exception.CommonResponse;
import com.misim.service.SmsService;
import com.misim.service.UserService;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "User API", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SmsService smsService;

    // 유저 정보 등록 - 과정 gmarket 기반으로 하자.
    // 약관 동의 -> 본인 인증 -> 유저 정보 기입 후 등록 버튼 클릭
    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @Parameter(name = "UserDto", description = "User 회원 가입을 위한 데이터.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공."),
            @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 유저입니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/signup")
    public void signupUser(@RequestBody UserDto userDto) {

        // 유저 데이터 검사 - invalid인 경우 exception 발생
        userDto.check();

        // 유저 정보 등록
        userService.registerUser(userDto);
    }

    // 본인 인증 - post requestbody base64 url 인코딩
    @GetMapping("/verifyAccountSMS")
    public void sendSMSVerificationCode(@RequestParam String phoneNumber) {
        Validator.validatePhoneNumber(phoneNumber);

        smsService.sendSMS(phoneNumber);
    }

    @PostMapping("/verifyAccountSMS")
    public ResponseEntity<?> checkSMSVerificationCode(@RequestBody SmsVerificationDto smsVerificationDto) {
        LocalDateTime current = java.time.LocalDateTime.now();

        smsVerificationDto.check();

        smsService.matchSMS(smsVerificationDto, current);

        return ResponseEntity.ok().body();
    }


    // 아이디 찾기 - 본인 인증 -> 아이디 찾기 결과
    @GetMapping("/help/findId")
    public void findId() {

    }


    // 비밀번호 리셋 - 아이디 입력 -> 본인 인증 -> 비밀번호 리셋 -> 임시 비밀번호 전달
    @GetMapping("/help/resetPassword")
    public void resetPassword() {
        
    }
    
    
    // 비밀번호 변경 - 로그인 -> 비밀번호 변경
    @GetMapping("/help/changePassword")
    public void changePassword() {

    }
}
