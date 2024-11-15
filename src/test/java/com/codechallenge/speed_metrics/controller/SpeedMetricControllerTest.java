package com.codechallenge.speed_metrics.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpeedMetricControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void submitLineSpeed_withUnknownLine_returnsNotFound() throws Exception {

        final JSONObject payload = new JSONObject();
        payload.put("line_id", 15L);

        mvc.perform(MockMvcRequestBuilders.post(SpeedMetricController.ROOT_PATH + SpeedMetricController.LINE_SPEED)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void fetchLineMetrics_withUnknownLine_returnsNotFound() throws Exception {

        final long unknownLineId = 23L;

        mvc.perform(MockMvcRequestBuilders.get(SpeedMetricController.ROOT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("lineId", Long.toString(unknownLineId)))
            .andExpect(status().isNotFound());
    }

}
