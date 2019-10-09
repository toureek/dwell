package com.dwell.it.controller;

import com.dwell.it.entities.House;
import com.dwell.it.enums.DataSingleton;
import com.dwell.it.exception.InternalMethodInvokeException;
import com.dwell.it.model.HttpJSONResponse;
import com.dwell.it.utils.FileInputOutputUtils;
import com.dwell.it.utils.database.DatabaseStorageUtils;
import com.dwell.it.webspider.CrawlerConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/fetch")
@RestController
public class CrawlerController {

    @Autowired
    private CrawlerConfigHelper crawlerConfigHelper;


    @RequestMapping(value = "/crawlers")
    public HttpJSONResponse fetchOriginalDataSource() throws InternalMethodInvokeException {
        if (DataSingleton.INSTANCE.isFetchingServiceRunning()) {
            return HttpJSONResponse.ok("网络爬虫正在获取网页基本数据 请耐心等待");
        }

        try {
            DataSingleton.INSTANCE.setFetchingServiceRunning(true);
            crawlerConfigHelper.setUpWebSpiderConfigAndStartCrawlerService();
        } finally {
            DataSingleton.INSTANCE.setFetchingServiceRunning(false);
            DataSingleton.INSTANCE.setDatasourceFetched(true);
        }
        return HttpJSONResponse.ok("网络爬虫已完成基本数据爬取工作");
    }


    /** 使用高德地图GEO 从原始地址数据 解析为坐标数据：
     * @return Json-Response
     */
    @RequestMapping(value = "/coordinates", method = RequestMethod.GET)
    public HttpJSONResponse batchFetchingGPSInfoAndUpdateDatabaseToLatest() {
        // 1 先数据库过滤 满足查询condition的数据记录
        // 2 带着address信息 去批量请求SDK 获取解析后的GPS信息
        // 3 将SDK返回的GPS信息 再批量修改进数据库

        return HttpJSONResponse.errorMessage("数据整理出现异常 请查询错误日志");
    }
}
