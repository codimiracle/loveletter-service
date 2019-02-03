package com.codimiracle.service.loveletter.controller;

import com.codimiracle.service.loveletter.model.RestModel;
import com.codimiracle.service.loveletter.util.RestUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Codimiracle
 */
@RequestMapping("/")
@RestController
public class ServiceController {

    @GetMapping
    public RestModel displayInfo() {
        return RestUtil.success();
    }

}
