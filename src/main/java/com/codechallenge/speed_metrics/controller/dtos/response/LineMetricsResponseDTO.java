package com.codechallenge.speed_metrics.controller.dtos.response;

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

    private Long numberOfRecords;

    private MetricResponseDTO metrics;

}
