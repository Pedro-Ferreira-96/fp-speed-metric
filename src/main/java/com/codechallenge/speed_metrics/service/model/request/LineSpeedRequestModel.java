package com.codechallenge.speed_metrics.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineSpeedRequestModel {

    private Long line_id;

    private Float speed;

    private Long timestamp;

}
