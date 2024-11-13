package com.codechallenge.speed_metrics.service;

import com.codechallenge.speed_metrics.service.config.CsvFileConfig;
import com.codechallenge.speed_metrics.service.impl.SpeedMetricServiceImpl;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import java.time.Instant;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SpeedMetricServiceImpl.class})
public class SpeedMetricServiceImplTest {

    private static final String TEST_CSV = "src/test/java/com/codechallenge/speed_metrics/utils/test.csv";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SpeedMetricService speedMetricService;

    @MockBean
    private CsvFileConfig csvFileConfig;

    @AfterEach
    void clearTestCsvFile() {


    }

    @Test
    @Disabled
    public void submitRecord_recordIsPublishedOnFile() {

        final long leftLimit = 1L;
        final long rightLimit = 10L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        final Random rd = new Random();

        final LineSpeedRequestModel model = LineSpeedRequestModel.builder()
            .line_id(generatedLong)
            .speed(rd.nextFloat())
            .timestamp(Instant.now().toEpochMilli())
            .build();

        Mockito.when(csvFileConfig.getPath()).thenReturn(TEST_CSV);

        speedMetricService.submitLineSpeed(model);

    }

    @Test
    @Disabled
    public void fetchRecord_recordAreReadFromFile() {

        submitRecord_recordIsPublishedOnFile();
    }

}