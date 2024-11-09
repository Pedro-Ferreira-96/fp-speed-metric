package com.codechallenge.speed_metrics.service.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(LineModel.class)
@ConfigurationProperties(prefix = "known-lines")
@Component
@Getter
@Setter
public class LineModel {

    public List<Long> id;

}
