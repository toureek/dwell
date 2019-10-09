package com.dwell.it.entities;

import lombok.Data;

import javax.persistence.Id;

@Data
public class House {

    @Id
    private Integer id;
    private Integer providerId;                                     // 发布该房源的中介Id (数据量不大的情况下 使用了外键 显示了providerName)

    private String title;                                           // 描述标题

    private String detailPageUrl;                                   // 详情页面URL

    private String area;                                            // 房屋面积

    private String aspect;                                          // 房屋朝向

    private String livingDiningKitchenInfo;                         // LDKDesc (LDK是日本的房屋描述文案)

    private String stockCount;                                      // 剩余房间的库存

    private String mainImageUrl;                                    // 房屋介绍主图片

    private String cityZone;                                        // 房屋城区信息

    private String infoTags;                                        // 房屋描述标签（多个) 存储的是字符串数组string[]以分号分割
    // 列表页面最多只显示7个， 详情页面全部显示

    private String lastUpdateTime;                                  // 上次更新的时间

    private String tradePrice;                                      // 交易价格（房租）

    private String tradePriceUnit;                                  // 交易费用的单位 （元/月）

    private String providerName;

    // location: geo
    private String geoInfo;                                         // 地理信息(经纬度，"116.468593,39.998774")


    public House() {
    }


    /**
     * constructor for standard fetching api
     *
     * @param providerId provider表的外键
     */
    public House(Integer providerId, String title, String detailPageUrl, String area, String aspect, String livingDiningKitchenInfo, String stockCount, String mainImageUrl, String cityZone, String infoTags, String lastUpdateTime, String tradePrice, String tradePriceUnit) {
        this.providerId = providerId;
        this.title = title;
        this.detailPageUrl = detailPageUrl;
        this.area = area;
        this.aspect = aspect;
        this.livingDiningKitchenInfo = livingDiningKitchenInfo;
        this.stockCount = stockCount;
        this.mainImageUrl = mainImageUrl;
        this.cityZone = cityZone;
        this.infoTags = infoTags;
        this.lastUpdateTime = lastUpdateTime;
        this.tradePrice = tradePrice;
        this.tradePriceUnit = tradePriceUnit;
    }


    public House(Integer id, Integer providerId, String title, String detailPageUrl, String area, String aspect, String livingDiningKitchenInfo, String stockCount, String mainImageUrl, String cityZone, String infoTags, String lastUpdateTime, String tradePrice, String tradePriceUnit, String providerName, String geoInfo) {
        this.id = id;
        this.providerId = providerId;
        this.title = title;
        this.detailPageUrl = detailPageUrl;
        this.area = area;
        this.aspect = aspect;
        this.livingDiningKitchenInfo = livingDiningKitchenInfo;
        this.stockCount = stockCount;
        this.mainImageUrl = mainImageUrl;
        this.cityZone = cityZone;
        this.infoTags = infoTags;
        this.lastUpdateTime = lastUpdateTime;
        this.tradePrice = tradePrice;
        this.tradePriceUnit = tradePriceUnit;
        this.providerName = providerName;
        this.geoInfo = geoInfo;
    }

}
