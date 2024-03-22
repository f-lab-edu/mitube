package com.misim.controller;

import com.misim.controller.model.Response.HomeResponse;
import com.misim.entity.VideoCategory;
import com.misim.exception.CommonResponse;
import com.misim.service.HomeService;
import com.misim.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final HomeService homeService;

    @GetMapping("/home")
    public CommonResponse<HomeResponse> home(@RequestParam Long userId) {

        HomeResponse response = homeService.getHome(userId);

        return CommonResponse
                .<HomeResponse>builder()
                .body(response)
                .build();
    }
}
