package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.util.List;

public interface SpeedMetricService {

    void submitLineSpeed(final LineSpeedRequestModel lineSpeedRequestModel);

    List<LineSpeedResponseModel> fetchLineMetrics(final Long lineId, final Long timeInterval);

}
