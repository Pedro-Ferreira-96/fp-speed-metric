package com.codechallenge.speed_metrics.facade;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.controller.dtos.response.LineMetricsResponseDTO;
import java.util.List;

public interface SpeedMetricFacade {

    void submitLineSpeed(final LineSpeedRequestDTO lineSpeedRequestDTO);

    List<LineMetricsResponseDTO> fetchLineMetrics(final Long lineId, final Long timeInterval);

}
