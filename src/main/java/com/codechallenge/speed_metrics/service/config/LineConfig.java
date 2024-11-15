package com.codechallenge.speed_metrics.service.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(LineConfig.class)
@Configuration
@ConfigurationProperties(prefix = "known-lines")
@Getter
@Setter
public class LineConfig {

    public List<Long> id;

}
