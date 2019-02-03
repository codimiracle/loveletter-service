package com.codimiracle.service.loveletter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Codimiracle
 */
@Getter
@Setter
public class Page {
    private int page = 1;
    private int size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }
}
