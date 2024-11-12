package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.util.List;

public interface SpeedMetricService {

    void submitLineSpeed(LineSpeedRequestModel lineSpeedRequestModel);

    List<LineSpeedResponseModel> fetchLineMetrics(Long lineId);

}
