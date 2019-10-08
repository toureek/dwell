package com.dwell.it.controller;

import com.dwell.it.enums.DataSingleton;
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
        if (DataSingleton.INSTANCE.isFetchingServiceRunning()) {
            return "网络爬虫正在进行数据爬取工作 请耐心等待程序完成";
        }

        try {
            DataSingleton.INSTANCE.setFetchingServiceRunning(true);
            crawlerConfigHelper.setUpWebSpiderConfigAndStartCrawlerService();
        } finally {
            DataSingleton.INSTANCE.setFetchingServiceRunning(false);
            DataSingleton.INSTANCE.setDatasourceFetched(true);
        }
        return "网络爬虫已完成基本数据爬取工作";
    }
}
