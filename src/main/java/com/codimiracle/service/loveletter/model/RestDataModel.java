package com.codimiracle.service.loveletter.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestDataModel<T> extends RestModel {
    private T data;

    public RestDataModel(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }
}
