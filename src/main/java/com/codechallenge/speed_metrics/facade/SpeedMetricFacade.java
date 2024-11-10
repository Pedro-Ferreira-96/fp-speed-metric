package com.codechallenge.speed_metrics.facade;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;

public interface SpeedMetricFacade {

    void submitLineSpeed(LineSpeedRequestDTO lineSpeedRequestDTO);

}
