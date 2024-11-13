package com.codechallenge.speed_metrics.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(CsvFileConfig.class)
@ConfigurationProperties(prefix = "csv-file")
@Component
@Getter
@Setter
public class CsvFileConfig {

    public String path;

}
