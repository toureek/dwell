package com.dwell.it.webspider;

import edu.uci.ics.crawler4j.crawler.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceParseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceParseHelper.class);


    /**
     * 处理爬虫当前获取下来的页面数据
     * @param page 当前页面
     */
    public void formatOriginalDataSourceOnWebPage(Page page) {
        if (page == null) {
            logger.error("empty page data source");
            return;
        }

        String urlPath = page.getWebURL().getURL().toLowerCase();
        System.out.println(urlPath);
    }
}
