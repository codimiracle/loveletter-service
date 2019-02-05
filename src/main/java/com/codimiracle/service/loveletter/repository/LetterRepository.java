package com.codimiracle.service.loveletter.repository;

import com.codimiracle.service.loveletter.entity.Letter;
import com.codimiracle.service.loveletter.model.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LetterRepository {
    @Select("SELECT * FROM letter_theme lt WHERE id = #{id} AND runId = #{runId}")
    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    Letter findById(@Param("id") String id, @Param("runId") String runId);

    @Select("SELECT COUNT(*) FROM love_letter WHERE runId = #{runId}")
    int countAll(@Param("runId") String runId);

    @Select("SELECT * FROM letter_theme WHERE runId = #{runId} LIMIT #{page.offset},#{page.limit}")
    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    List<Letter> findAll(@Param("page") Page page, @Param("runId") String runId);

    @Select("SELECT COUNT(*) FROM love_letter WHERE themeId = #{themeId} AND runId = #{runId}")
    int countAllByThemeId(@Param("themeId") String themeId, @Param("runId") String runId);

    @Select("SELECT * FROM letter_theme WHERE theme_id = #{themeId} AND runId = #{runId} LIMIT #{page.offset},#{page.limit}")
    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    List<Letter> findAllByThemeId(@Param("page") Page page, @Param("themeId") String themeId, @Param("runId") String runId);

    @Select("SELECT COUNT(*) FROM love_letter WHERE sender = #{sender} AND runId = #{runId}")
    int countAllBySender(@Param("sender") String sender, @Param("runId") String runId);

    @Select("SELECT * FROM letter_theme WHERE sender = #{sender} AND runId = #{runId} LIMIT #{page.offset},#{page.limit}")

    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    List<Letter> findAllBySender(@Param("page") Page page, String sender, @Param("runId") String runId);

    @Select("SELECT COUNT(*) FROM love_letter WHERE receiver = #{receiver} AND runId = #{runId}")
    int countAllByReceiver(@Param("receiver") String receiver, @Param("runId") String runId);

    @Select("SELECT * FROM letter_theme WHERE receiver = #{receiver} AND runId = #{runId} AND runId = #{runId} LIMIT #{page.offset},#{page.limit}")
    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    List<Letter> findAllByReciever(@Param("page") Page page, @Param("receiver") String receiver, @Param("runId") String runId);

    @Select("SELECT COUNT(*) FROM love_letter WHERE runId = #{runId} AND (sender LIKE CONCAT(CONCAT('%',#{keyword}),'%') OR receiver LIKE CONCAT(CONCAT('%',#{keyword}),'%') OR body LIKE CONCAT(CONCAT('%',#{keyword}),'%')) ")
    int countAllByKeyword(@Param("keyword") String keyword, @Param("runId") String runId);

    @Select("SELECT * FROM letter_theme WHERE runId = #{runId} AND (sender LIKE CONCAT(CONCAT('%',#{keyword}),'%') OR receiver LIKE CONCAT(CONCAT('%',#{keyword}),'%') OR body LIKE CONCAT(CONCAT('%',#{keyword}),'%')) LIMIT #{page.offset},#{page.limit}")
    @Results({
            @Result(property = "title.id", column = "theme_id"),
            @Result(property = "title.subject", column = "theme_subject"),
            @Result(property = "title.title", column = "theme_title"),
            @Result(property = "title.episode", column = "theme_episode"),
            @Result(property = "title.url", column = "theme_url"),
            @Result(property = "title.runId", column = "theme_runId"),
            @Result(property = "title.publishTime", column = "theme_publishTime")
    })
    List<Letter> findAllByKeyword(@Param("page") Page page, @Param("keyword") String keyword, @Param("runId") String runId);

    @Insert("INSERT into love_letter (id, sender, receiver, body, runId, themeId) values (#{letter.id}, #{letter.sender}, #{letter.receiver}, #{letter.body}, #{letter.runId}, #{letter.title.id})")
    void insert(@Param("letter") Letter letter);
}
