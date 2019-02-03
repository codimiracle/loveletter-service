package com.codimiracle.service.loveletter.repository;

import com.codimiracle.service.loveletter.entity.Theme;
import com.codimiracle.service.loveletter.model.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ThemeRepository {
    @Select("SELECT * FROM love_theme WHERE id = #{id} AND runId = #{runId}")
    Theme findById(@Param("id") String id, @Param("runId") String runId);

    @Select("SELECT * FROM love_theme WHERE runId = #{runId} LIMIT #{page.offset},#{page.limit}")
    List<Theme> findAll(@Param("page") Page page, @Param("runId") String runId);

    @Select("SELECT count(*) FROM love_theme WHERE runId = #{runId}")
    int countAll(@Param("runId") String runId);

    @Insert("INSERT INTO love_theme (id, episode, subject, title, url, publishTime,runId) VALUES (#{theme.id}, #{theme.episode}, #{theme.subject}, #{theme.title}, #{theme.url}, #{theme.publishTime}, #{theme.runId})")
    void insert(@Param("theme") Theme theme);
}
