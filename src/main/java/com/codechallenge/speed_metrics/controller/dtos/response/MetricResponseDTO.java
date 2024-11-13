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

    private Float averageSpeed;

    private Float maxSpeed;

    private Float minSpeed;


    public static final MetricResponseDTO from(MetricResponseModel model) {

        return MetricResponseDTO.builder()
            .averageSpeed(model.getAvg())
            .maxSpeed(model.getMax())
            .minSpeed(model.getMin())
            .build();
    }
}
