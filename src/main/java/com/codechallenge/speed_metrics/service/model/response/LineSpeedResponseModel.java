package com.codechallenge.speed_metrics.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineSpeedResponseModel {

    private Long line_id;

    private MetricResponseModel metricResponse;

}
