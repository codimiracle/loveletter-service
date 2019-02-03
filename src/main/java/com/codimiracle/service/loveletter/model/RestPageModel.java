package com.codimiracle.service.loveletter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Codimiracle
 */
@Getter
@Setter
public class RestPageModel<T> extends RestDataModel<List<T>> {
    private int page;
    private int size;
    private int total;

    public RestPageModel(int code, String message, int page, int size, int total, List<T> data) {
        super(code, message, data);
        this.page = page;
        this.size = size;
        this.total = total;
    }
}
