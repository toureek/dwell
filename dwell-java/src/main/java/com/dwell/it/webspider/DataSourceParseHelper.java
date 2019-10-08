package com.dwell.it.webspider;

import com.dwell.it.entities.House;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DataSourceParseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceParseHelper.class);

    private LinkedHashMap<String, String> detailPageUrlMap;  // 详情页面url数据集合

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
        ArrayList<House> houseArrayList = new ArrayList<>();
        for (Elements elements : elementsList) {
            houseArrayList = parsedListPageContentAndPrepareDBRequirements(elements);
            System.out.println(houseArrayList);
            //TODO: 批量插入数据库
        }
    }


    /**
     * 根据web-html-tags 解析出一级列表页面上的HouseObjects
     * @param elementsList  web-html-tags
     * @return houseArrayList
     */
    private ArrayList<House> parsedListPageContentAndPrepareDBRequirements(Elements elementsList) {
        if (elementsList == null || elementsList.size() == 0)    return null;

        // 每一页的数据有30条，考虑到装载因子过大时会有hash散列冲突与性能问题，粗算让装载因子小于0.75，所以Map选择初始容量为40。
        ArrayList<House> houseArrayList = new ArrayList<>(30);
        if (detailPageUrlMap == null) {    // lazy-loading
            detailPageUrlMap = new LinkedHashMap<>(40);
        }

        for (Element element : elementsList) {
            String url = elementSelectForNextPageLink(element);
            if (!(detailPageUrlMap.containsValue(url))) {  // TODO: 这里hashmap的key 性能需要优化
                detailPageUrlMap.put(url, url);
                // TODO: 具体解析与组装House Model, 并将其添加进列表中
            }
        }
        return houseArrayList;
    }



    /**
     * 获取【详情页】的URL：  element.selectForLink()
     * @param element Element: element  each elementObject from elementsList
     * @return  String: linkURL  点击当前item后 所要跳转到下一级页面的URL
     */
    private String elementSelectForNextPageLink(Element element) {
        Elements detailURL = element.select(".content__list--item");     // 详情页面URL
        if (!(detailURL.isEmpty())) {
            String fetchedURL = detailURL.attr("href");  // HTML中URL的标签
            if ((fetchedURL != null) && fetchedURL.length() > 0) {  //  这是page1的第index0号房屋信息的linkURL，即详情页的URL
                return "https://xa.zu.ke.com" + fetchedURL;
            }
        }
        return "";
    }


    /**
     * 查询房屋标题描述 以及 详情页面URL
     * @param element   Element: element  each elementObject from elementsList
     * @return  String[] {title, imageURL}  返回两个元素的字符串数组，且第一个元素为房屋标题，后一个是房屋主图URL
     */
    private static String[] elementSelectForTitleAndMainImageURL(Element element) {
        Elements wrapperObject = element.select(".content__list--item--aside");
        String[] result = new String[] {"", ""};
        if (!(wrapperObject.isEmpty())) {
            Elements titleAndImageObject = wrapperObject.select(".lazyload");
            if (!(titleAndImageObject.isEmpty())) {
                String fetchedTitleText = titleAndImageObject.attr("alt");
                String fetchedImageURL = titleAndImageObject.attr("data-src");
                result = new String[] {fetchedTitleText, fetchedImageURL};
            }
        }
        return result;
    }


    /**
     * 查询到 elements.select(库存/城区信息 / 房屋面积、朝向、3DLKDesc)的信息
     *
     * 房屋面积、朝向、3DLKDesc
     *                       |
     *                       |——  房屋库存。   在HTML-DIV-CSS中的结构如上所示
     * 但显示顺序是 1.库存/城区信息  2.房屋面积 3.朝向 4.3DLKDesc(一室一厅一卫)
     *
     * @param element   Element: element  each elementObject from elementsList
     * @return      String[] {a, b, c, d, e}       返回5个元素的字符串数组
     *              分别对应 a.城区信息  b.房屋面积 c.朝向 d.1DLKDesc(一室一厅一卫) e.库存； 如果没有城区信息，库存显示在第一个位置
     *
     * PS:  DLKDesc是日本对房屋的描述情况(Dinner-LivingRoom-Kitchen)
     */
    private String[] elementSelectForBasicInfo(Element element) {
        String cityZone, areaSize, aspect, descLDKInfo, stockInfo;
        cityZone = areaSize = aspect = descLDKInfo = stockInfo = "";

        Elements wrapperStockObject = element.select(".content__list--item--des");
        if (wrapperStockObject.isEmpty()) {
            return new String[] {cityZone, areaSize, aspect, descLDKInfo, stockInfo};
        }

        String objText = wrapperStockObject.text();
        if (objText.length() > 0) {
            String[] itemsList = objText.split("/");
            for (int i = 0; i < itemsList.length-1; i++) {
                if (itemsList[i].contains("㎡")) {
                    areaSize = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                   // area
                } else if (itemsList[i].contains("室")) {
                    descLDKInfo = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                // LDKDesc
                } else if (itemsList[i].contains("东") ||
                        itemsList[i].contains("南") ||
                        itemsList[i].contains("西") ||
                        itemsList[i].contains("北")) {
                    aspect = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                     // aspect
                } else {
                    cityZone = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                   // cityZone
                }
            }
        }

        Elements stockObj = wrapperStockObject.select(".room__left");  // stock
        if (!(stockObj.isEmpty())) {
            stockInfo = stockObj.text().length() > 0 ? stockObj.text() : "";
        }
        return new String[] {cityZone, areaSize, aspect, descLDKInfo, stockInfo};
    }


    /**
     * 查询到房屋发布时间
     * @param element   Element: element  each elementObject from elementsList
     * @return          String: lastUpdatedTimeInfo
     */
    private String elementSelectForLastUpdatedDatetime(Element element) {
        Elements lastUpdateTimeElement = element.select(".content__list--item--time");
        if (!(lastUpdateTimeElement.isEmpty())) {
            String lastUpdateTimeText = lastUpdateTimeElement.text();
            return lastUpdateTimeText.length() > 0 ? lastUpdateTimeText : "";
        }
        return "";
    }


    /**
     * 查询到房屋Tags信息
     * @param element   Element: element  each elementObject from elementsList
     * @return          String[] tagsInfo
     */
    private String[] elementSelectForTagsInfo(Element element) {
        Elements tagsElement = element.select(".content__list--item--bottom");
        if (!(tagsElement.isEmpty())) {
            String tagsText = tagsElement.text();
            if (tagsText.length() > 0) {
                return tagsText.split(" ");
            }
            return new String[] {""};
        }
        return new String[] {""};
    }


    /**
     * 房屋费用信息 （金额+计价单位）
     * @param element   Element: element  each elementObject from elementsList
     * @return          String[] {price, paymentType}
     */
    private String[] elementSelectForPriceAndPaymentType(Element element) {
        Elements priceElement = element.select(".content__list--item-price");
        if (!(priceElement.isEmpty())) {
            String priceText = priceElement.text();
            String[] priceInfoAndPaymentTypeObj = priceText.split(" ");
            if (priceInfoAndPaymentTypeObj.length == 2) {
                return priceInfoAndPaymentTypeObj;
            }
            return new String[] {"", ""};
        }
        return new String[] {"", ""};
    }


    /**
     * 查询到房屋所属运营品牌 （不是房屋自己的建筑开发商）
     * @param element   Element: element  each elementObject from elementsList
     * @return          String: brand  返回运营商品牌信息
     */
    private String elementSelectForOpearatorBrand(Element element) {
        Elements brandElement = element.select(".content__list--item--brand");
        if (!(brandElement.isEmpty())) {
            String operationBrand = brandElement.text();
            return operationBrand.length() > 0 ? operationBrand : "";
        }
        return "";
    }
}
