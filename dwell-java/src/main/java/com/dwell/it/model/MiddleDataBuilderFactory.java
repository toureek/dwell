package com.dwell.it.model;

import com.dwell.it.entities.House;
import com.dwell.it.enums.WebPageDataSourceEnum;
import com.dwell.it.utils.TextInputOutputUtils;
import org.jsoup.helper.StringUtil;

public class MiddleDataBuilderFactory {


    /**
     * 工厂方法构造数据模型 但并不直接与DB交互。列表页面house对象的工厂构造
     *
     * @param detailUrl               详情页面URL
     * @param titleAndImageUrl        Title/ImageURL
     * @param basicLDKInfo            BasicLDKTags
     * @param lastUpdatedTime         LastUpdatedTime
     * @param tagsInfo                TagsInfo
     * @param priceAndPaymentTypeInfo Price
     * @param providerName            ProviderName
     * @return houseItem
     */
    public static House buildFirstClassWebPageHouse(String detailUrl,
                                                    String[] titleAndImageUrl,
                                                    String[] basicLDKInfo,
                                                    String lastUpdatedTime,
                                                    String[] tagsInfo,
                                                    String[] priceAndPaymentTypeInfo, String providerName) {
        House house = new House();
        house.setDetailPageUrl(TextInputOutputUtils.safeTextContent(detailUrl));

        String title = titleAndImageUrl[0];
        String mainImageURL = titleAndImageUrl[1];
        house.setTitle(TextInputOutputUtils.safeTextContent(title));
        house.setMainImageUrl(TextInputOutputUtils.safeTextContent(mainImageURL));

        house.setCityZone(TextInputOutputUtils.safeTextContent(basicLDKInfo[0]));
        house.setArea(TextInputOutputUtils.safeTextContent(basicLDKInfo[1]));
        house.setAspect(TextInputOutputUtils.safeTextContent(basicLDKInfo[2]));
        house.setLivingDiningKitchenInfo(TextInputOutputUtils.safeTextContent(basicLDKInfo[3]));
        house.setStockCount(TextInputOutputUtils.safeTextContent(basicLDKInfo[4]));

        house.setLastUpdateTime(TextInputOutputUtils.safeTextContent(lastUpdatedTime));

        house.setInfoTags(StringUtil.join(tagsInfo, ","));

        house.setTradePrice(TextInputOutputUtils.safeTextContent(priceAndPaymentTypeInfo[0]));
        house.setTradePriceUnit(TextInputOutputUtils.safeTextContent(priceAndPaymentTypeInfo[1]));

        house.setProviderName(TextInputOutputUtils.safeTextContent(providerName));

        return house;
    }


    // ---------------  以下是二级页面 构造中间数据的地方  ---------------


    // 二级页面的HTML-CSS-DIV class elements tags: 住宅页面类型的BasicInfoTags
    public static final String[] residenceTypeBasicInfoTags = new String[]{
            "发布：", "入住：", "租期：", "看房：", "楼层：", "电梯：", "车位：", "用水：", "用电：", "燃气：", "采暖："
    };

    // 二级页面的HTML-CSS-DIV class elements tags: 住宅页面类型的FacilityInfoTags
    public static final String[] residenceTypeFacilityInfoTags = new String[]{
            "电视", "冰箱", "洗衣机", "空调", "热水器", "床", "暖气", "宽带", "衣柜", "天然气"
    };


    /**
     * 获取二级页面 住宅类型 有多个HTML标签，并返回该HTML-Tag的集合
     *
     * @return String[] tagsHTML
     */
    public static String[] buildResidenceTypeBasicInfoHtmlTags() {
        return new String[]{
                WebPageDataSourceEnum.OBJECT_2ND_TITLE_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_ONLINE_DATE_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_IDENTIFIER_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_WRAPPER_BANNER_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_STARTER_PRICE_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_STARTER_PRICE_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_TAGS_LIST_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_SUB_TAGS_LIST_TAG.toString(),
                WebPageDataSourceEnum.ITEM_2ND_OPERATOR_AVATAR_TAG.toString(),
                WebPageDataSourceEnum.ITEM_2ND_CONTACT_NAME_TAG.toString(),
                WebPageDataSourceEnum.ITEM_2ND_OPERATOR_TAG.toString(),
                WebPageDataSourceEnum.ITEM_2ND_TEL_NUMBER_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_BASIC_INFO_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_FACILITIES_TAG.toString(),
                WebPageDataSourceEnum.OBJECT_2ND_INTRODUCTION_TAG.toString()
        };
    }


    /**
     * 二级页面 住宅类型的配套设备标签
     *
     * @return 住宅类型的配套设备标签的字符串数组
     */
    public static String[] buildResidenceTypeFacilitiesHtmlTags() {
        return new String[]{
                WebPageDataSourceEnum.TELEVISION_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.REFRIGERATOR_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.WASHING_MACHINE_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.AIR_CONDITIONER_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.WATER_HEATER_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.BED_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.HEATING_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.WIFI_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.WARDROBE_HIDDEN_TAG.toString(),
                WebPageDataSourceEnum.GAS_HIDDEN_TAG.toString()
        };
    }
}
