package com.codechallenge.speed_metrics.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetricResponseDTO {

    private Float avg;

    private Float max;

    private Float min;

}
