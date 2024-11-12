package com.codechallenge.speed_metrics.controller.dtos.response;

import com.codechallenge.speed_metrics.service.model.response.MetricResponseModel;
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


    public static final MetricResponseDTO from(MetricResponseModel model) {

        return MetricResponseDTO.builder()
            .avg(model.getAvg())
            .max(model.getMax())
            .min(model.getMin())
            .build();
    }
}
