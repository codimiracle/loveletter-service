package com.codimiracle.service.loveletter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Codimiracle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theme {
    private String id;
    private String subject;
    private String episode;
    private String title;
    private String url;
    private Date publishTime;
    private String runId;
}
