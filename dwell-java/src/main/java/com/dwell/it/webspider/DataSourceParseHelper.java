package com.dwell.it.webspider;

import com.dwell.it.entities.House;
import com.dwell.it.enums.WebPageDataSourceEnum;
import com.dwell.it.model.ModelFactory;
import com.dwell.it.utils.MD5Generator;
import com.dwell.it.utils.TextInputOutputUtils;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.jsoup.Jsoup
        ;
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

    /** 解释一下为什么在这里使用LinkedHashMap这个数据结构
     *  1.基于数据源页面的数据量有限，只有100页数据，每页都有30条记录，并且这些记录是存在重复的，决定使用LinkedHashMap来存储.
     *  2.使用LinkedHashMap存储时，url不直接作为key,而是先md5处理一下，使原始很长的url哈希为16字节大小，3000条数据的key任何电脑都可以读取
     *  3.如果数据量大到上亿条时，不建议使用LinkedHashMap，因为Map有装载因子，虽然天然支持动态扩容(基于链表)，实际使用时内存占用更大
     *  4.数据量上亿条时，可以将访问记录记录分散在10000个文件中，每个文件最大500Mb, 让URL先hash再对10000去模运算，得到的结果就是url应保存的文件编号
     *  然后依次对10000个数据进行处理，这样即使单台机器内存只有1GB,也可以使用这种方法处理数据.(与load-balance的处理类似)
     */
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

        if (urlPath.startsWith(WebPageDataSourceEnum.RESIDENCE_2ND_CLASS_PAGE_PREFIX.toString())) {
            // TODO: 详情页面解析
        } else {
            constructFirstClassPageData(elementsList);
            // TODO: I/O Saving Fetched URLs
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
        if (urlPath.toLowerCase().startsWith(WebPageDataSourceEnum.RESIDENCE_2ND_CLASS_PAGE_PREFIX.toString())) {
            return new String[] {""};    // TODO: 二级页面原始节点tag
        } else if (urlPath.toLowerCase().startsWith(WebPageDataSourceEnum.DATA_SOURCE_BASE_PAGE_PREFIX.toString())) {
            return new String[] {WebPageDataSourceEnum.ITEM_OBJECT_TAG.toString()};     // 一级页面是列表  所以只有一个元素
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
            String md5Key = TextInputOutputUtils.safeTextContent(MD5Generator.generateMD5Identifier(url));
            if (!(detailPageUrlMap.containsValue(md5Key))) {
                detailPageUrlMap.put(md5Key, url);

                String liteUrlPath = url.replaceAll(WebPageDataSourceEnum.DATA_SOURCE_BASE_PAGE_PREFIX.toString(),"");
                String[] titleAndMainImageURL = elementSelectForTitleAndMainImageURL(element);          // 标题描述+房屋1张图片URL
                String[] basicLDKInfo = elementSelectForBasicInfo(element);                             // 房屋基本信息
                String lastUpdatedTime = elementSelectForLastUpdatedDatetime(element);                  // 房屋发布时间
                String[] tagsInfo = elementSelectForTagsInfo(element);                                  // 房屋信息标签
                String[] priceAndPaymentTypeInfo = elementSelectForPriceAndPaymentType(element);        // 房屋费用信息
                String providerName = elementSelectForOpearatorBrand(element);                          // 房屋所属运营品牌

                House house = ModelFactory.buildFirstClassWebPageHouse(liteUrlPath,
                        titleAndMainImageURL,
                        basicLDKInfo,
                        lastUpdatedTime,
                        tagsInfo,
                        priceAndPaymentTypeInfo,
                        providerName);

                if ((house != null) && (!(houseArrayList.contains(house)))) {  // TODO: 处理provider表的外键关联
                    houseArrayList.add(house);
                }
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
        Elements detailURL = element.select(WebPageDataSourceEnum.ITEM_TITLE_DESC_TAG.toString());     // 详情页面URL
        if (!(detailURL.isEmpty())) {
            String fetchedURL = detailURL.attr("href");  // HTML中URL的标签
            if ((fetchedURL != null) && fetchedURL.length() > 0) {  //  这是page1的第index0号房屋信息的linkURL，即详情页的URL
                return WebPageDataSourceEnum.DATA_SOURCE_BASE_PAGE_PREFIX.toString() + fetchedURL;
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
        Elements wrapperObject = element.select(WebPageDataSourceEnum.ITEM_TITLE_DESC_TAG.toString());
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

        Elements wrapperStockObject = element.select(WebPageDataSourceEnum.ITEM_BASIC_INFO_TAG.toString());
        if (wrapperStockObject.isEmpty()) {
            return new String[] {cityZone, areaSize, aspect, descLDKInfo, stockInfo};
        }

        String objText = wrapperStockObject.text();
        if (objText.length() > 0) {
            String[] itemsList = objText.split("/");
            for (int i = 0; i < itemsList.length-1; i++) {
                if (itemsList[i].contains(WebPageDataSourceEnum.OBJECT_AREA_TAG.toString())) {
                    areaSize = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                   // area
                } else if (itemsList[i].contains(WebPageDataSourceEnum.OBJECT_DKLs_TAG.toString())) {
                    descLDKInfo = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                // LDKDesc
                } else if (itemsList[i].contains(WebPageDataSourceEnum.OBJECT_ASPECT_EAST_TAG.toString()) ||
                        itemsList[i].contains(WebPageDataSourceEnum.OBJECT_ASPECT_SOUTH_TAG.toString()) ||
                        itemsList[i].contains(WebPageDataSourceEnum.OBJECT_ASPECT_WEST_TAG.toString()) ||
                        itemsList[i].contains(WebPageDataSourceEnum.OBJECT_ASPECT_NORTH_TAG.toString())) {
                    aspect = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                     // aspect
                } else {
                    cityZone = itemsList[i].trim().length() > 0 ? itemsList[i].trim() : "";                   // cityZone
                }
            }
        }

        Elements stockObj = wrapperStockObject.select(WebPageDataSourceEnum.ITEM_STOCK_INFO_TAG.toString());  // stock
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
        Elements lastUpdateTimeElement = element.select(WebPageDataSourceEnum.ITEM_LAST_UPDATED_TIME_TAG.toString());
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
        Elements tagsElement = element.select(WebPageDataSourceEnum.ITEM_TAGS_INFO_TAG.toString());
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
        Elements priceElement = element.select(WebPageDataSourceEnum.ITEM_PRICE_AND_PAYMENT_TAG.toString());
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
        Elements brandElement = element.select(WebPageDataSourceEnum.ITEM_OPERATION_BRAND_TAG.toString());
        if (!(brandElement.isEmpty())) {
            String operationBrand = brandElement.text();
            return operationBrand.length() > 0 ? operationBrand : "";
        }
        return "";
    }
}
