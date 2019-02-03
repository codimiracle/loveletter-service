package com.codimiracle.service.loveletter.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Codimiracle
 */
@Data
public class CrawlingResult implements Serializable {
    public static final int CREATED = 0;
    public static final int RUNNING = 1;
    public static final int FINISHED = 2;
    public static final int SPIDER_ERROR = -1;
    public static final int SYSTEM_ERROR = -2;

    private String runId;
    private Integer status = CREATED;
    private String logfile;
    private String outfile;
    private Integer exitValue;
    private Date createdTime;
    private Date terminatedTime;
}
