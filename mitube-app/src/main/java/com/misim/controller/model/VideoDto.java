package com.misim.controller.model;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class VideoDto implements Checker{

    private String title;
    private String description;
    private String nickname;
    private String token;
    private Integer categoryId;

    @Override
    public void check() {

        // null 체크
        if (title == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_TITLE);
        }

        if (description == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_DESCRIPTION);
        }

        if (nickname == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (token == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_VIDEO_TOKEN);
        }

        if (categoryId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CATEGORY);
        }
    }
}
