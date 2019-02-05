package com.codimiracle.service.loveletter.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface StatisticsRepository {

    @Select("SELECT sender `name`, COUNT(sender) `count` FROM love_letter WHERE runId = #{runId} GROUP BY sender ORDER BY `count` DESC")
    List<Map<String, Object>> statisticSender(@Param("runId") String runId);

    @Select("SELECT receiver `name`, COUNT(receiver) `count` FROM love_letter WHERE runId = #{runId} GROUP BY receiver ORDER BY `count` DESC")
    List<Map<String, Object>> statisticReceiver(@Param("runId") String runId);

    @Select("SELECT letter.body FROM love_letter letter WHERE letter.body like concat(concat('%', #{keyword}), '%') AND runId = #{runId} ")
    List<String> statisticKeyword(@Param("keyword") String keyword, @Param("runId") String runId);

    @Select("SELECT * from word_analysis WHERE runId = #{runId}")
    List<Map<String, Object>> statisticWordFrequency(@Param("runId") String runId);
}
