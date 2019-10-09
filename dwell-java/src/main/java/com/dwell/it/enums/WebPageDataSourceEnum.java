package com.dwell.it.enums;

public enum WebPageDataSourceEnum {
    // 基础配置
    SUB_URL_SEED("rt200600000001/#contentList"),
    WEB_CRAWLER_BASE_SEED("https://xa.zu.ke.com/zufang"),
    OUTPUT_FILENAME("fetched_url.txt"),

    // URL前缀
    DATA_SOURCE_BASE_PAGE_PREFIX("https://xa.zu.ke.com"),
    FIRST_CLASS_WEB_PAGE_PREFIX("https://xa.zu.ke.com/zufang/pg"),
    APARTMENT_2ND_CLASS_PAGE_PREFIX("https://xa.zu.ke.com/apartment"),
    RESIDENCE_2ND_CLASS_PAGE_PREFIX("https://xa.zu.ke.com/zufang/xa"),

    // placeHolder
    USER_AVATAR_URL_PLACEHOLDER("http://image1.ljcdn.com/rent-front-image/444b415acf9e282f34b8bebe56cabbc2.1512721489026_323fb813-60b3-440a-bb98-b8cf5ffc02eb"),

    // 一级页面的HTML-CSS-DIV class elements tags: 列表页面
    ITEM_OBJECT_TAG(".content__list--item"),
    ITEM_TITLE_DESC_TAG(".content__list--item--aside"),
    ITEM_BASIC_INFO_TAG(".content__list--item--des"),
    ITEM_STOCK_INFO_TAG(".room__left"),
    ITEM_OPERATION_BRAND_TAG(".content__list--item--brand"),
    ITEM_LAST_UPDATED_TIME_TAG(".content__list--item--time"),
    ITEM_TAGS_INFO_TAG(".content__list--item--bottom"),
    ITEM_PRICE_AND_PAYMENT_TAG(".content__list--item-price"),

    OBJECT_RENT_TEXT_TAG("租"),
    OBJECT_DKLs_TAG("室"),
    OBJECT_AREA_TAG("㎡"),
    OBJECT_ASPECT_EAST_TAG("东"),
    OBJECT_ASPECT_SOUTH_TAG("南"),
    OBJECT_ASPECT_WEST_TAG("西"),
    OBJECT_ASPECT_NORTH_TAG("北"),
    OBJECT_AVATAR_URL_TAG("style"),

    // 以下为二级页面HTML的tag
    OBJECT_2ND_TITLE_TAG(".content__title"),
    OBJECT_2ND_ONLINE_DATE_TAG(".content__subtitle"),
    OBJECT_2ND_IDENTIFIER_TAG(".house_code"),
    OBJECT_2ND_WRAPPER_BANNER_TAG(".content__article__slide__wrapper"),             // 住宅详情页 banner父节点tag
    OBJECT_2ND_STARTER_PRICE_TAG(".content__aside--title"),                         // 起步价
    OBJECT_2ND_TAGS_LIST_TAG(".content__aside--tags"),                              // 住宅标签关键字
    OBJECT_2ND_SUB_TAGS_LIST_TAG(".content__article__table"),                       // 租住类型、DLK、面积、朝向
    OBJECT_2ND_BASIC_INFO_TAG(".content__article__info"),                           // 基本信息
    OBJECT_2ND_FACILITIES_TAG(".content__article__info2"),                          // 配套设施
    OBJECT_2ND_INTRODUCTION_TAG(".content__article__info3"),                        // 房源描述

    ITEM_2ND_BANNER_ITEM_TAG(".content__article__slide__item"),                     // 每张轮播图object对应的tag
    ITEM_2ND_BANNER_IMAGE_TAG("src"),                                               // 每张具体图片对应的tag（公寓类型）
    ITEM_2ND_OPERATOR_AVATAR_TAG(".content__aside__list--icon"),                    // 运营商头像
    ITEM_2ND_CONTACT_NAME_TAG(".contact_name"),                                     // 联系人名称
    ITEM_2ND_OPERATOR_TAG(".content__aside__list--subtitle"),                       // 运营商标签
    ITEM_2ND_TEL_NUMBER_TAG(".content__aside__list--bottom"),                       // 联系电话

    // 需要删除掉的多余文本 (详情页面的描述【文本标题】)
    TEXT_WILL_BE_REMOVED_A("房屋信息"),
    TEXT_WILL_BE_REMOVED_B("基本信息"),
    TEXT_WILL_BE_REMOVED_C("房源描述"),

    // 详情页面中的家具设备tag
    TELEVISION_HIDDEN_TAG(".fl.oneline.television_no"),
    REFRIGERATOR_HIDDEN_TAG(".fl.online.refrigerator_no"),
    WASHING_MACHINE_HIDDEN_TAG(".fl.online.washing_machine_no"),
    AIR_CONDITIONER_HIDDEN_TAG(".fl.oneline.air_conditioner_no"),
    WATER_HEATER_HIDDEN_TAG(".fl.oneline.water_heater_no"),
    BED_HIDDEN_TAG(".fl.oneline.bed_no"),
    HEATING_HIDDEN_TAG(".fl.oneline.heating_no"),
    WIFI_HIDDEN_TAG(".fl.oneline.wifi_no"),
    WARDROBE_HIDDEN_TAG(".fl.oneline.wardrobe_no"),
    GAS_HIDDEN_TAG(".fl.oneline.natural_gas_no"),

    ;


    private final String htmlTag;

    WebPageDataSourceEnum(final String tagName) {
        htmlTag = tagName;
    }

    @Override
    public String toString() {
        return this.htmlTag;
    }
}
