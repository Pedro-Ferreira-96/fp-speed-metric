package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpeedMetricServiceImplTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SpeedMetricService speedMetricService;

    @Test
    public void submitRecord_recordIsPublishedOnFile() {

        final Random rd = new Random();

        final LineSpeedRequestModel model = LineSpeedRequestModel.builder()
            .line_id(1L)
            .speed(rd.nextFloat())
            .timestamp(Instant.now().toEpochMilli())
            .build();

        speedMetricService.submitLineSpeed(model);
    }

    @Test
    public void fetchRecord_recordAreReadFromFile() {

        final Long line_id = 1L;

        final List<LineSpeedResponseModel> result = speedMetricService.fetchLineMetrics(line_id);

        System.out.println("result: " + result);
    }

}
