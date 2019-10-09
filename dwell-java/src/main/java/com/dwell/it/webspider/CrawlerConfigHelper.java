package com.dwell.it.webspider;

import com.dwell.it.enums.DataSingleton;
import com.dwell.it.enums.WebPageDataSourceEnum;
import com.dwell.it.exception.InternalMethodInvokeException;
import com.dwell.it.utils.FileInputOutputUtils;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.http.client.config.CookieSpecs.IGNORE_COOKIES;

@Component
public class CrawlerConfigHelper {

    @Value("${webspider.path}")
    private String webspiderPath;

    private final Integer NUMBER_OF_CRAWLERS = 1;
    private final Integer MAX_DEPTH_OF_CRAWLING = 1;
    private final Integer FETCHED_MAX_LIST_PAGE_COUNT = 100;                   // 【1至10页的一级列表页面 每页30条记录】
    private final Integer HTTP_REQUEST_DELAY_NANO_TIME = 1000;                 //  1000毫秒=1秒

    private static final Logger logger = LoggerFactory.getLogger(CrawlerConfigHelper.class);


    /**
     * 配置爬虫自定义config 并启动爬虫服务.  先执行一级页面的数据爬取，再执行二级页面的数据获取
     */
    public void setUpWebSpiderConfigAndStartCrawlerService() throws InternalMethodInvokeException {
        CrawlConfig config = setUpCrawlerConfiguration();
        try {  // 在一级页面请求完成之后 调整爬取页面数量的config 继续去请求二级页面的数据源
            try {
                CrawlController firstController = setUpCrawlerController(config);
                if (firstController == null) return;

                addFirstClassWebPageDataSourceUrl(firstController);
                firstController.start(MyWebCrawler.class, NUMBER_OF_CRAWLERS);
            } finally {
                String fileName = WebPageDataSourceEnum.OUTPUT_FILENAME.toString();
                List<String> fetchedURLsList = FileInputOutputUtils.fetchedEachLineOfContentFromTextFile(fileName);
                DataSingleton.INSTANCE.setRequestURLsList(fetchedURLsList);
                config.setMaxPagesToFetch(DataSingleton.INSTANCE.getRequestURLsList().size());
            }

            CrawlController secondController = setUpCrawlerController(config);
            for (String url : DataSingleton.INSTANCE.getRequestURLsList()) {  // 只获取住宅类型的数据
                if (url.toLowerCase().startsWith(WebPageDataSourceEnum.RESIDENCE_2ND_CLASS_PAGE_PREFIX.toString())) {
                    secondController.addSeed(url);
                }
            }
            secondController.start(MyWebCrawler.class, NUMBER_OF_CRAWLERS);
        } finally {
            logger.info("\n-----------   All Crawler's jobs are finished  -----------");
        }
    }


    /**
     * 配置爬虫基本属性
     *
     * @return CrawlConfig: config 文件
     */
    private CrawlConfig setUpCrawlerConfiguration() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(webspiderPath);
        config.setPolitenessDelay(HTTP_REQUEST_DELAY_NANO_TIME);  // 设置请求的频率-两次请求的间隔至少是1秒
        config.setMaxDepthOfCrawling(MAX_DEPTH_OF_CRAWLING);      // 设置请求的网页的深度  设置2 为两层  默认值-1 无限深度
        config.setIncludeBinaryContentInCrawling(false);
        config.setIncludeHttpsPages(true);
        config.setResumableCrawling(false);                       // 默认配置是false；设置成true，性能降低

        // MARK: - 在这里花的时间比较长，Http请求不带上cookies的话 能够节省很多要数据 而且并不影响爬虫获取的主要数据
        config.setCookiePolicy(IGNORE_COOKIES);                   // 将CookiesPolicy设置为全部忽略，来获取页面上的数据
        config.setMaxPagesToFetch(FETCHED_MAX_LIST_PAGE_COUNT);   // 设置爬取的最大网页数 (默认值-1 无限制)
        return config;
    }


    /**
     * 配置爬虫controller
     *
     * @param config 爬虫controller配置
     * @return 爬虫controller
     */
    private CrawlController setUpCrawlerController(CrawlConfig config) throws InternalMethodInvokeException {
        PageFetcher pageFetcher = new PageFetcher(config);        // 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();  // 实例化爬虫机器人配置
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController resultController = null;
        try {
            resultController = new CrawlController(config, pageFetcher, robotstxtServer);
            // 人为制造一个异常: double x = 1 / 0;  可以抛出自定义异常 InternalMethodInvokeException 进行测试
        } catch (Exception e) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            throw new InternalMethodInvokeException(InternalMethodInvokeException.INTERNAL_METHOD_INVOKE_PREFIX + "" + runningMethodName + "()");
        }
        return resultController;
    }


    /**
     * 获取一级页面的数据，包含详情页面URLs
     *
     * @param controller 爬虫实例
     */
    private void addFirstClassWebPageDataSourceUrl(CrawlController controller) {
        if (controller == null) return;

        for (int i = 1; i <= FETCHED_MAX_LIST_PAGE_COUNT; i++) {
            String url = String.format("%s/pg%d%s",
                    WebPageDataSourceEnum.WEB_CRAWLER_BASE_SEED.toString(),
                    i,
                    WebPageDataSourceEnum.SUB_URL_SEED.toString());
            controller.addSeed(url);
        }
    }
}
