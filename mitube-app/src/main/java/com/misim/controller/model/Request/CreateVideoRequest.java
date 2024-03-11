package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVideoRequest implements Checker {

    private String title;
    private String description;
    private String nickname;
    private String token;
    private Integer categoryId;

    @Override
    public void check() {

        // null 체크
        if (title == null || title.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_TITLE);
        }

        if (description == null || description.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_DESCRIPTION);
        }

        if (nickname == null || nickname.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (token == null || token.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_VIDEO_TOKEN);
        }

        if (categoryId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CATEGORY);
        }
    }
}
