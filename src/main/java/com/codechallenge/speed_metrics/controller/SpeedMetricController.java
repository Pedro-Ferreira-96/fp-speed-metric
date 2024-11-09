package com.codechallenge.speed_metrics.controller;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.controller.dtos.response.LineMetricsResponseDTO;
import com.codechallenge.speed_metrics.service.model.LineModel;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private final LineModel lineModel;

    @Autowired
    public SpeedMetricController(final LineModel lineModel) {

        this.lineModel = lineModel;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<LineMetricsResponseDTO> fetchLineMetrics() {

        return List.of();
    }

    @PostMapping(value = LINE_SPEED)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity lineSpeed(@RequestBody final LineSpeedRequestDTO lineSpeedRequestDTO) {

        if (!lineModel.getId().contains(lineSpeedRequestDTO.getLine_id())) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if(recordOlderThan(lineSpeedRequestDTO.getTimestamp(),60)){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean recordOlderThan(final Long timestamp ,final Integer thresholdInMinutes) {

        Duration timeBetween = Duration.between(Instant.now(), Instant.ofEpochMilli(timestamp));

        return timeBetween.toMinutes() < thresholdInMinutes;
    }

}
