package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import java.time.Instant;
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

        final LineSpeedRequestModel model = LineSpeedRequestModel.builder()
            .line_id(1L)
            .speed(35.5f)
            .timestamp(Instant.now().toEpochMilli())
            .build();

        speedMetricService.submitLineSpeed(model);
    }

}
