package com.codimiracle.service.loveletter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Codimiracle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Letter {
    private String id;
    private String body;
    private String receiver;
    private String sender;
    @JsonIgnore
    private String runId;
    private Theme theme = new Theme();
}
