package com.codimiracle.service.loveletter.service;

import com.codimiracle.service.loveletter.entity.CrawlingResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpiderServiceTest {

    @Autowired
    SpiderService spiderService;

    @Test
    public void crawl() {
        ListenableFuture<CrawlingResult> future = spiderService.crawlFully();
        while (!future.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            CrawlingResult result = future.get();
            assertNotNull(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadCrawledData() {
    }
}