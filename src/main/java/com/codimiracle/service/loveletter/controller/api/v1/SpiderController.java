package com.codimiracle.service.loveletter.controller.api.v1;

import com.codimiracle.service.loveletter.entity.CrawlingResult;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.model.RestModel;
import com.codimiracle.service.loveletter.model.RestPageModel;
import com.codimiracle.service.loveletter.service.SpiderService;
import com.codimiracle.service.loveletter.util.RestUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author Codimiracle
 */
@RestController
@RequestMapping("/api/v1/spider")
public class SpiderController {

    @Resource
    private SpiderService spiderService;

    @GetMapping("/status")
    public RestModel status() {
        return RestUtil.success();
    }

    @PostMapping("/recrawl")
    public RestModel recrawl(String type, String jwt) {
        if ("all".equals(type)) {
            spiderService.crawlFully();
        } else if ("increment".equals(type)) {
            spiderService.crawlIncremently();
        } else {
            return RestUtil.fail(300034, "unknown operation.");
        }
        return RestUtil.success();
    }

    @GetMapping("/result")
    public RestPageModel<CrawlingResult> displayCrawlingResulList(@ModelAttribute Page page) {
        return RestUtil.success(page, spiderService.countAll(), spiderService.findAll(page));
    }

    @GetMapping("/result/{runId}/log")
    public RestModel displayLogByRunId(@PathVariable String runId) {
        return RestUtil.success();
    }

    @GetMapping("/result/{runId}/log/download")
    public ResponseEntity<byte[]> downloadLogByRunId(@PathVariable String runId) {
        CrawlingResult result = spiderService.findByRunId(runId);
        if (result != null) {
            HttpHeaders headers = new HttpHeaders();
            File logfile = new File(result.getLogfile());

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", result.getLogfile());
            try {
                return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(logfile),
                        headers, HttpStatus.CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    @GetMapping("/result/{runId}/data")
    public RestModel displayData(@PathVariable String runId) {
        return RestUtil.success();
    }

    @GetMapping("/result/{runId}/data/download")
    public ResponseEntity<byte[]> downloadDataByRunId(@PathVariable String runId) {
        CrawlingResult result = spiderService.findByRunId(runId);
        if (result != null) {
            HttpHeaders headers = new HttpHeaders();
            File outfile = new File(result.getOutfile());

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", result.getOutfile());
            try {
                return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(outfile),
                        headers, HttpStatus.CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @GetMapping("/lastCrawlInfo")
    public RestModel lastCrawlInfo() {
        return RestUtil.success();
    }
}
