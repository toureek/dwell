package com.dwell.it.enums;

public enum HttpInteractiveEnum {

    HTTP_REQUEST_FAILED_MESSAGE("请求失败，请稍后重试"),

    HTTP_RESPONSE_FORMAT("application/json"),
    AJAX_REQUEST_URL_PREFIX("https://xa.zu.ke.com/aj/house/brokers"),
    AJAX_HOUSE_CODE_PARAMS_NAME("house_codes"),

    ;


    private final String name;

    HttpInteractiveEnum(final String text) {
        name = text;
    }

    @Override
    public String toString() {
        return this.name;
    }
}