package com.codimiracle.service.loveletter.controller.api.v1;

import com.codimiracle.service.loveletter.model.RestDataModel;
import com.codimiracle.service.loveletter.service.StatisticsService;
import com.codimiracle.service.loveletter.util.RestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    @Value("${spider.crawled-data.default-run-id}")
    private String defaultRunId;

    @Resource
    private StatisticsService statisticsService;

    @GetMapping("/sender")
    public RestDataModel<List<Map<String, Object>>> displayStatisticsOfSender(@RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(statisticsService.statisticSender(runId));
    }

    @GetMapping("/receiver")
    public RestDataModel<List<Map<String, Object>>> displayStatisticsOfReceiver(@RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(statisticsService.statisticReceiver(runId));
    }

    @GetMapping("/keyword")
    public RestDataModel<Map<String, Long>> displayStatisticsOfContent(@RequestParam String keyword, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(statisticsService.statisticKeyword(keyword, runId));
    }

    @GetMapping("/wordFrequency")
    public RestDataModel<List<Map<String, Object>>> displayStatisticsOfWordFrequency(@RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(statisticsService.statisticWordFrequency(runId));
    }
}
