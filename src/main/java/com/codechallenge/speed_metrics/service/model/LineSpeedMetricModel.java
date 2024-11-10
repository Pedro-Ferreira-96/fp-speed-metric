package com.codechallenge.speed_metrics.service.model;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineSpeedMetricModel {

    private Float speed;

    private Long timestamp;

    public static LineSpeedMetricModel from(LineSpeedRequestModel lineSpeedRequestModel) {

        return LineSpeedMetricModel.builder()
            .speed(lineSpeedRequestModel.getSpeed())
            .timestamp(lineSpeedRequestModel.getTimestamp())
            .build();
    }

}
