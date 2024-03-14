package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartWatchingVideoResponse {

    private Long watchingTime;
}
