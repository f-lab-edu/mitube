package com.misim.controller;

import com.misim.controller.model.UserDto;
import com.misim.exception.CommonResponse;
import com.misim.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
    public void signupUser(@RequestBody UserDto userDto, HttpServletRequest request) {

        // 유저 데이터 검사 - invalid인 경우 exception 발생
        userDto.check();

        // url.equals(http://localhost:8080/users/signup) == true
        String url = request.getRequestURL().toString();

        url = url.replace("/signup", "");

        // 유저 정보 등록
        userService.registerUser(userDto, url);
    }

    // 이메일 본인 인증 - post requestbody base64 url 인코딩
    @GetMapping("/verifyAccount")
    public void verifyAccountByEmail(@RequestParam String token) {

        userService.verifyAccount(token);
    }
}
