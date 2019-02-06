package com.codimiracle.service.loveletter.service;

import com.codimiracle.service.loveletter.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    @Resource
    private StatisticsRepository statisticRepository;

    public List<Map<String, Object>> statisticSender(String runId) {
        return statisticRepository.statisticSender(runId);
    }

    public List<Map<String, Object>> statisticReceiver(String runId) {
        return statisticRepository.statisticReceiver(runId);
    }

    public Map<String, Long> statisticKeyword(String runId, String keyword) {
        List<String> list = statisticRepository.statisticKeyword(keyword, runId);
        long count = list.stream().mapToInt(body -> {
            int index = 0, c = 0;
            while ((index = body.indexOf(keyword, index)) > -1) {
                c++;
            }
            return c;
        }).sum();
        Map<String, Long> map = new HashMap<>();
        map.put(keyword, count);
        return map;
    }

    public List<Map<String, Object>> statisticWordFrequency(String runId) {
        return statisticRepository.statisticWordFrequency(runId);
    }
}
