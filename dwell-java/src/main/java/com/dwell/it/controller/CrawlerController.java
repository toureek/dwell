package com.dwell.it.controller;

import com.dwell.it.exception.InternalMethodInvokeException;
import com.dwell.it.webspider.CrawlerConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/fetch")
@RestController
public class CrawlerController {

    @Autowired
    private CrawlerConfigHelper crawlerConfigHelper;

    @RequestMapping(value = "/data")
    public String fetchOriginalDataSource() throws InternalMethodInvokeException {
        crawlerConfigHelper.setUpWebSpiderConfigAndStartCrawlerService();
        return "网络爬虫已完成基本数据爬取工作";
    }
}
