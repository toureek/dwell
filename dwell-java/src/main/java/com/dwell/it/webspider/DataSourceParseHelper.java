package com.dwell.it.webspider;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        String webHTML = convertDataSourceToHtmlText(page);
        List<Elements> elementsList = convertHtmlTextToElements(webHTML, urlPath);
        if (elementsList == null) {
            logger.error("empty page data source");
            return;
        }

        if (urlPath.startsWith("https://xa.zu.ke.com/zufang/xa")) {
            // TODO: 详情页面解析
        } else {
            constructFirstClassPageData(elementsList);
            // TODO: 详情页面URL需要以I/O流的形式 存储到文件
        }
    }


    /**
     * convert normal-page-source into html
     * @param page 当前页面
     * @return html
     */
    private String convertDataSourceToHtmlText(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {  // isHTML
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();           // 获取页面HTML
            return html.length() > 0 ? html : "";
        }
        return "";
    }


    /**
     * 根据HTML源码 解析指定的数据区域数据集合 elementsList.
     * All elements objects which required in DVI.classes from HTML and Encapsulated in List.
     * @param webHTML html-source
     * @return elementsList
     */
    private List<Elements> convertHtmlTextToElements(String webHTML, String url) {
        if (webHTML.length() == 0)    return null;

        List<Elements> list = new LinkedList<>();
        String[] htmlTags = keyHtmlElementTags(url);
        Document document = Jsoup.parse(webHTML);
        for (String tag : htmlTags) {
            Elements elementsList = document.select(tag);
            if (elementsList != null) {
                list.add(elementsList);
            }
        }
        return list;
    }


    /**
     * 根据当前获取页面page上 指定需要获取数据的位置 并返回其对应HTML-CSS-DVI标签
     * @param urlPath 当前页面
     * @return String[] htmlTags   抓取数据对象在HTML页面上的标签的字符串数组
     */
    private String[] keyHtmlElementTags(String urlPath) {
        if (urlPath.toLowerCase().startsWith("https://xa.zu.ke.com/zufang/xa")) {
            return new String[] {""};    // TODO: 二级页面原始节点tag
        } else if (urlPath.toLowerCase().startsWith("https://xa.zu.ke.com")) {
            return new String[] {".content__list--item"};     // 一级页面是列表  所以只有一个元素
        } else {
            return new String[] {""};
        }
    }



    /**
     * 格式化一级页面的数据源 保存在文件中并打印出来
     * @param elementsList 页面HTML中 需要的数据Tag标签元素集合
     */
    private void constructFirstClassPageData(List<Elements> elementsList) {
        ArrayList<Object> houseArrayList = new ArrayList<>();   // HouseModel List
        // ToDO: 构造House Model并将其添加进列表
    }

    
}
