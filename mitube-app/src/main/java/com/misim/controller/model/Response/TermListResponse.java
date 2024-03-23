package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TermListResponse {

    private List<TermResponse> termResponseList;
}
