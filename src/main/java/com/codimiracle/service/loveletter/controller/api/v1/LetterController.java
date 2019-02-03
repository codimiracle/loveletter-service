package com.codimiracle.service.loveletter.controller.api.v1;

import com.codimiracle.service.loveletter.config.SpiderCrawledDataConfig;
import com.codimiracle.service.loveletter.entity.Letter;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.model.RestModel;
import com.codimiracle.service.loveletter.model.RestPageModel;
import com.codimiracle.service.loveletter.service.LetterService;
import com.codimiracle.service.loveletter.util.RestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Codimiracle
 */
@RestController
@RequestMapping("/api/v1/letter")
public class LetterController {
    @Value(value = "${spider.crawled-data.default-run-id}")
    private String defaultRunId;

    @Resource
    LetterService letterService;

    @GetMapping("/{id}")
    public RestModel displayItem(@PathVariable String id,  @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(letterService.findById(id, runId));
    }

    @GetMapping("/findAllByThemeId")
    public RestPageModel<Letter> displayListByThemeId(@ModelAttribute Page page, @RequestParam(required = false) String themeId,  @RequestParam String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, letterService.countAllByThemeId(themeId, runId), letterService.findAllByThemeId(page, themeId, runId));
    }

    @GetMapping("/findAll")
    public RestPageModel<Letter> displayList(@ModelAttribute Page page, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, letterService.countAll(runId), letterService.findAll(page, runId));
    }

    @GetMapping("/findAllBySender")
    public RestPageModel<Letter> displayListBySender(@ModelAttribute Page page, String sender, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, letterService.countAllBySender(sender, runId), letterService.findAllBySender(page, sender, runId));
    }

    @GetMapping("/findAllByReceiver")
    public RestPageModel<Letter> displayListByReceiver(@ModelAttribute Page page, String receiver, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, letterService.countAllReceiver(receiver, runId), letterService.findAllByReceiver(page, receiver, runId));
    }

    @GetMapping("/findAllByKeyword")
    public RestPageModel<Letter> displayListByKeyword(@ModelAttribute Page page, String keyword, @RequestParam(required = false) String runId) {
        if (Objects.isNull(runId)) {
            runId = defaultRunId;
        }
        return RestUtil.success(page, letterService.countAllByKeyword(keyword, runId), letterService.findAllByKeyword(page, keyword, runId));
    }
}
