package com.misim.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "응답 결과")
public class CommonResponse<T> {

    @Schema(description = "응답 코드")
    private int code;
    
    @Schema(description = "응답 메시지")
    private String message;

    // 에러시 null
    @Schema(description = "응답 데이터")
    private T body;

}
