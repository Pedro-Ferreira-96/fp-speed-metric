package com.codechallenge.speed_metrics.Controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LineMetricsResponseDTO {

    private Long line_id;

    private MetricResponseDTO metricResponseDTO;

}
