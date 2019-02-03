package com.codimiracle.service.loveletter.repository;

import com.codimiracle.service.loveletter.entity.CrawlingResult;
import com.codimiracle.service.loveletter.model.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CrawlingResultRepository {
    @Insert("INSERT INTO crawling_result (`runId`,`logfile`,`outfile`,`exitValue`,`status`, `createdTime`, `terminatedTime`) VALUES (#{result.runId},#{result.logfile},#{result.outfile},#{result.exitValue},#{result.status}, NOW(), NOW())")
    void insert(@Param("result") CrawlingResult result);

    @Update("UPDATE crawling_result r SET r.status = #{result.status}, r.exitValue = #{result.exitValue}, terminatedTime = NOW() WHERE r.runId = #{result.runId}")
    void update(@Param("result") CrawlingResult result);

    @Update("UPDATE crawling_result r SET r.status = #{result.status}, r.exitValue = #{result.exitValue}, terminatedTime = NOW() WHERE r.runId = #{result.runId} AND r.status = #{updatingStatus}")
    void updateIdempotently(@Param("result") CrawlingResult result, @Param("updatingStatus") int updatingStatus);

    @Select("SELECT * FROM crawling_result LIMIT #{page.offset}, #{page.limit}")
    List<CrawlingResult> findAll(@Param("page") Page page);

    @Select("SELECT count(*) FROM crawling_result")
    int countAll();

    @Select("SELECT * FROM crawling_result cr WHERE cr.runId = #{runId}")
    CrawlingResult findByRunId(@Param("runId") String runId);
}
