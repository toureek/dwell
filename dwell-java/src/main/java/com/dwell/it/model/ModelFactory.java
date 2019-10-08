package com.dwell.it.model;

import com.dwell.it.entities.House;
import com.dwell.it.utils.TextInputOutputUtils;
import org.jsoup.helper.StringUtil;

public class ModelFactory {

    /**
     * 工厂方法构造数据模型 但并不直接与DB交互。列表页面house对象的工厂构造
     * @param detailUrl         详情页面URL
     * @param titleAndImageUrl  Title/ImageURL
     * @param basicLDKInfo      BasicLDKTags
     * @param lastUpdatedTime   LastUpdatedTime
     * @param tagsInfo          TagsInfo
     * @param priceAndPaymentTypeInfo  Price
     * @param providerName      ProviderName
     * @return  houseItem
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
}
