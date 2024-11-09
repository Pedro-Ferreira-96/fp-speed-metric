package com.codechallenge.speed_metrics.Controller;

import com.codechallenge.speed_metrics.Controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.Controller.dtos.response.LineMetricsResponseDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = SpeedMetricController.ROOT_PATH)
public class SpeedMetricController {

    public static final String ROOT_PATH = "/api/metrics";

    public static final String LINE_SPEED = "/linespeed";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<LineMetricsResponseDTO> fetchLineMetrics() {

        return List.of();
    }

    @PostMapping(value = LINE_SPEED)
    @ResponseStatus(HttpStatus.CREATED)
    public void lineSpeed(@RequestBody final LineSpeedRequestDTO lineSpeedRequestDTO) {

    }

}
