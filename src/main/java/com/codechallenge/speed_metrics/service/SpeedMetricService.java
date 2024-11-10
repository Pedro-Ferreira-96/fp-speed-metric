package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;

public interface SpeedMetricService {

    void submitLineSpeed(LineSpeedRequestModel lineSpeedRequestModel);

}
