package com.codechallenge.speed_metrics.facade;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.controller.dtos.response.LineMetricsResponseDTO;
import com.codechallenge.speed_metrics.controller.dtos.response.MetricResponseDTO;
import com.codechallenge.speed_metrics.service.SpeedMetricService;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    public List<LineMetricsResponseDTO> fetchLineMetrics(final Long lineId, final Long timeInterval) {

        final List<LineSpeedResponseModel> lineSpeedResponseModel = speedMetricService.fetchLineMetrics(lineId, timeInterval);

        return lineSpeedResponseModel.stream()
            .map(model -> LineMetricsResponseDTO.builder()
                .line_id(model.getLine_id())
                .numberOfRecords(model.getNumberOfRecords())
                .metrics(MetricResponseDTO.from(model.getMetricResponse()))
                .build())
            .collect(Collectors.toList());
    }

}
