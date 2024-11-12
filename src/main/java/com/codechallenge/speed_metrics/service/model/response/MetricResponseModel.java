package com.codechallenge.speed_metrics.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricResponseModel {

    private Float avg;

    private Float max;

    private Float min;

}
