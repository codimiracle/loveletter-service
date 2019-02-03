package com.codimiracle.service.loveletter.util;

import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.model.RestDataModel;
import com.codimiracle.service.loveletter.model.RestModel;
import com.codimiracle.service.loveletter.model.RestPageModel;

import java.util.List;

public class RestUtil {
    public static RestModel success() {
        return new RestModel(0,"success");
    }

    public static RestModel fail(int code, String message) {
        return new RestModel(code, message);
    }

    public static <T> RestDataModel<T> success(T theme) {
        return new RestDataModel<>(0, "success", theme);
    }

    public static <T> RestPageModel<T> success(Page page, int total, List<T> list) {
        return new RestPageModel<>(0, "success", page.getPage(), page.getSize(), total, list);
    }

    public static <T> RestPageModel<T> fail(int code, String message, Page page, int total) {
        return new RestPageModel<>(code, message, page.getPage(), page.getSize(), total, null);
    }
}
