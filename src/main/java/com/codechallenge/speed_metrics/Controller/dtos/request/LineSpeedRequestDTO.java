package com.codechallenge.speed_metrics.Controller.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LineSpeedRequestDTO {

    private Long line_id;

    private Float speed;

    private Long timestamp;

}
