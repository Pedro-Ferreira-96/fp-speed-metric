package com.codechallenge.speed_metrics.facade;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.service.SpeedMetricService;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SpeedMetricFacadeImpl implements SpeedMetricFacade{

    private final SpeedMetricService speedMetricService;

    @Override
    public void submitLineSpeed(final LineSpeedRequestDTO lineSpeedRequestDTO) {

        final LineSpeedRequestModel lineSpeedRequestModel = LineSpeedRequestModel.builder()
            .line_id(lineSpeedRequestDTO.getLine_id())
            .speed(lineSpeedRequestDTO.getSpeed())
            .timestamp(lineSpeedRequestDTO.getTimestamp())
            .build();

        speedMetricService.submitLineSpeed(lineSpeedRequestModel);
    }

}
