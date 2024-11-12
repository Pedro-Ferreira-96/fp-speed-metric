package com.codechallenge.speed_metrics.controller;

import com.codechallenge.speed_metrics.controller.dtos.request.LineSpeedRequestDTO;
import com.codechallenge.speed_metrics.controller.dtos.response.LineMetricsResponseDTO;
import com.codechallenge.speed_metrics.facade.SpeedMetricFacade;
import com.codechallenge.speed_metrics.service.model.LineModel;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = SpeedMetricController.ROOT_PATH)
public class SpeedMetricController {

    public static final String ROOT_PATH = "/api/metrics";
    public static final String LINE_SPEED = "/linespeed";

    private final SpeedMetricFacade speedMetricFacade;
    private final LineModel lineModel;

    @GetMapping(value = "/{lineId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LineMetricsResponseDTO>> fetchLineMetrics(
        @PathVariable(required = false) final Long lineId) {

        if (!lineModel.getId().contains(lineId)) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(speedMetricFacade.fetchLineMetrics(lineId), HttpStatus.OK);
    }

    @PostMapping(value = LINE_SPEED)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> lineSpeed(@RequestBody final LineSpeedRequestDTO lineSpeedRequestDTO) {

        if (!lineModel.getId().contains(lineSpeedRequestDTO.getLine_id())) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if(recordOlderThan(lineSpeedRequestDTO.getTimestamp(),60)){

            speedMetricFacade.submitLineSpeed(lineSpeedRequestDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        speedMetricFacade.submitLineSpeed(lineSpeedRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean recordOlderThan(final Long timestamp ,final Integer thresholdInMinutes) {

        Duration timeBetween = Duration.between(Instant.now(), Instant.ofEpochMilli(timestamp));

        return timeBetween.toMinutes() < thresholdInMinutes;
    }

}
