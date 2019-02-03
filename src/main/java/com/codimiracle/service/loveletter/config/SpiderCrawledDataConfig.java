package com.codimiracle.service.loveletter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "spider.crawled-data")
public class SpiderCrawledDataConfig {
    private String defaultRunId;
}
