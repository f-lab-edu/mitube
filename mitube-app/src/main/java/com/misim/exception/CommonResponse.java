package com.misim.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "응답 결과")
public class CommonResponse<T> {

    @Schema(description = "응답 코드")
    private int code;
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "응답 데이터")
    private T body;

}
