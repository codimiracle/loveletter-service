package com.codimiracle.service.loveletter.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.codimiracle.service.loveletter.entity.CrawlingResult;
import com.codimiracle.service.loveletter.entity.Letter;
import com.codimiracle.service.loveletter.entity.Theme;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.repository.CrawlingResultRepository;
import com.codimiracle.service.loveletter.repository.LetterRepository;
import com.codimiracle.service.loveletter.repository.ThemeRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Codimiracle
 */
@Service
@Slf4j
public class SpiderService {
    private static final Pattern pattern = Pattern.compile(".+墙(\\d+)期.+");
    @Resource
    private CrawlingResultRepository crawlingResultRepository;
    @Resource
    private ThemeRepository themeRepository;
    @Resource
    private LetterRepository letterRepository;

    private String getRunId() {
        String now = DateFormatUtils.format(new Date(), "yyyy.MM.dd-HH:mm:ss");
        return DigestUtils.md5Hex(Crypt.crypt("loveletter-" + now));
    }

    private String getCommand(String outfile, String logfile) {
        StringBuilder builder = new StringBuilder();
        builder.append("scrapy crawl loveletter-xiaohan -o ");
        builder.append(outfile);
        builder.append(" --logfile=");
        builder.append(logfile);
        return builder.toString();
    }

    private CrawlingResult getCrawlingResult(String runId, String outfile, String logfile) {
        CrawlingResult crawlingResult = new CrawlingResult();
        crawlingResult.setRunId(runId);
        crawlingResult.setLogfile(logfile);
        crawlingResult.setOutfile(outfile);
        return crawlingResult;
    }

    private String getThemeId(String themeTitle, Date publishTime) {
        Matcher matcher = pattern.matcher(themeTitle);
        String episodeNum;
        if (matcher.matches()) {
            if (themeTitle.contains("不是没你不行，但有你更好")) {
                episodeNum = "17";
            } else if (themeTitle.contains("54期") && !themeTitle.contains("为你卸掉所有铠甲")) {
                episodeNum = "60";
            }else {
                episodeNum = matcher.group(1);
            }
            episodeNum = StringUtils.leftPad(episodeNum, 8, '0');
        } else {
            return String.format("1200100%s", DateFormatUtils.format(publishTime, "yyyyMMdd"));
        }
        return "1200000" + episodeNum;
    }

    private String getLetterId(String themeId, Integer letterIndex) {
        return themeId + "00" + StringUtils.leftPad("" + letterIndex, 2, '0');
    }

    @Async
    @Transactional(rollbackFor = IOException.class)
    public void loadCrawledData(String runId) throws IOException {
        CrawlingResult result = crawlingResultRepository.findByRunId(runId);
        if (Objects.nonNull(result)) {
            try {
                InputStream inputStream = Files.newInputStream(Paths.get(result.getOutfile()));
                String rawData = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
                inputStream.close();
                List<CrawledData> crawledDataList = JSON.parseArray(rawData, CrawledData.class);
                String[] rawThemeData = null;
                Map<String, Theme> map = new HashMap<>();
                for (CrawledData crawlData : crawledDataList) {
                    Theme theme = map.get(crawlData.theme);
                    if (Objects.isNull(theme)) {
                        theme = new Theme();
                        theme.setTitle(crawlData.theme);
                        rawThemeData = crawlData.theme.split("\\|");
                        theme.setEpisode(rawThemeData[0]);
                        theme.setSubject(crawlData.subject);
                        theme.setUrl(crawlData.url);
                        theme.setPublishTime(crawlData.getPublishTime());
                        theme.setId(getThemeId(crawlData.theme, crawlData.getPublishTime()));
                        theme.setRunId(runId);
                        map.put(crawlData.theme, theme);
                        log.debug("read theme data of crawled data: [{}]", theme);
                        themeRepository.insert(theme);
                    }
                    Letter letter = new Letter();
                    letter.setBody(crawlData.body);
                    letter.setReceiver(crawlData.receiver);
                    letter.setSender(crawlData.sender);
                    letter.setTheme(theme);
                    letter.setRunId(runId);
                    letter.setId(getLetterId(theme.getId(), crawlData.letterIndex));
                    log.debug("read letter data of crawled data: [{}]", letter);
                    letterRepository.insert(letter);
                }
            } catch (IOException e) {
                log.error("can not load the data of crawled data by runId [{}], it throws [{}]", runId, e);
                throw e;
            }
        }
    }

    @Async
    public ListenableFuture<CrawlingResult> crawlFully() {
        String runId = getRunId();
        String logfile = "crawled/" + runId + ".log";
        String outfile = "crawled/" + runId + ".json";
        String command = getCommand(outfile, logfile);
        CrawlingResult result = getCrawlingResult(runId, outfile, logfile);
        crawlingResultRepository.insert(result);
        try {
            Process process = Runtime.getRuntime().exec(command);
            result.setStatus(CrawlingResult.RUNNING);
            crawlingResultRepository.updateIdempotently(result, CrawlingResult.CREATED);
            while (process.isAlive()) {
                Thread.sleep(2000);
            }
            result.setExitValue(process.exitValue());
            if (result.getExitValue() == 0) {
                result.setStatus(CrawlingResult.FINISHED);
                loadCrawledData(runId);
            } else {
                result.setStatus(CrawlingResult.SPIDER_ERROR);
            }
            crawlingResultRepository.updateIdempotently(result, CrawlingResult.RUNNING);
            return AsyncResult.forValue(result);
        } catch (Exception e) {
            result.setStatus(CrawlingResult.SYSTEM_ERROR);
            log.error("spider service terminated:", e);
            if (e instanceof IOException) {
                crawlingResultRepository.updateIdempotently(result, CrawlingResult.CREATED);
            } else {
                crawlingResultRepository.updateIdempotently(result, CrawlingResult.RUNNING);
            }
            return AsyncResult.forExecutionException(e);
        }
    }

    public CrawlingResult findByRunId(String runId) {
        return crawlingResultRepository.findByRunId(runId);
    }

    public List<CrawlingResult> findAll(Page page) {
        return crawlingResultRepository.findAll(page);
    }

    public int countAll() {
        return crawlingResultRepository.countAll();
    }

    public void crawlIncremently() {
    }

    @Setter
    @Getter
    private static class CrawledData {

        @JSONField(name = "ep")
        private String theme;
        @JSONField(name = "to")
        private String receiver;
        @JSONField(name = "from")
        private String sender;
        @JSONField(name = "content")
        private String body;
        private String subject;
        @JSONField(name = "index")
        private Integer letterIndex;
        private Date publishTime;
        @JSONField(name = "link")
        private String url;
    }
}
