package com.dwell.it.utils;

import com.dwell.it.enums.WebPageDataSourceEnum;

public class TextInputOutputUtils {

    /**
     * 安全文本
     * @param inputText 入参文本
     * @return 安全文本
     */
    public static String safeTextContent(String inputText) {
        return inputText.length() > 0 ? inputText : "";
    }



    /**
     * 针对本应用 获取对URL长链接去掉主域名后的短链接
     * @param originalUrl 原始链接
     * @return 去掉主域名后的短链接
     */
    public static String safeShortUrlWithoutBaseUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0)    return "";

        String pathFilter = originalUrl.replaceAll(WebPageDataSourceEnum.DATA_SOURCE_BASE_PAGE_PREFIX.toString(), "");
        return pathFilter;
    }


}