package com.codimiracle.service.loveletter.controller.api.v1;

import com.codimiracle.service.loveletter.config.SpiderCrawledDataConfig;
import com.codimiracle.service.loveletter.entity.Theme;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.model.RestDataModel;
import com.codimiracle.service.loveletter.model.RestPageModel;
import com.codimiracle.service.loveletter.service.ThemeService;
import com.codimiracle.service.loveletter.util.RestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.ConfigFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Codimiracle
 */
@RestController
@RequestMapping("/api/v1/theme")
public class ThemeController {
    @Value(value = "${spider.crawled-data.default-run-id}")
    private String defaultRunId;

    @Resource
    ThemeService themeService;

    @GetMapping("/{id}")
    public RestDataModel<Theme> displayItem(@PathVariable String id, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(themeService.findById(id, runId));
    }

    @GetMapping("/findAll")
    public RestPageModel<Theme> displayList(@ModelAttribute Page page,  @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, themeService.countAll(runId),themeService.findAll(page, runId));
    }
}
