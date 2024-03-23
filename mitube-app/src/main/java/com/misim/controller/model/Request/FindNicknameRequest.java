package com.misim.controller.model.Request;


import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindNicknameRequest implements Checker {

    @Schema(name = "token", description = "토큰", example = "AIHR==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;
    
    @Override
    public void check() {

        if (this.token == null || token.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_SMS_TOKEN);
        }
    }
}
