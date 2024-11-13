package com.codechallenge.speed_metrics.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(CsvFileConfig.class)
@Configuration
@ConfigurationProperties(prefix = "csv-file")
@Getter
@Setter
public class CsvFileConfig {

    public String path;

}
