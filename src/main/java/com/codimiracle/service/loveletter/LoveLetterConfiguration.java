package com.codimiracle.service.loveletter;

import com.codimiracle.service.loveletter.config.SpiderCrawledDataConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@EnableConfigurationProperties(SpiderCrawledDataConfig.class)
public class LoveLetterConfiguration extends AsyncConfigurerSupport {
}
