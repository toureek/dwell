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
