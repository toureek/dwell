package com.dwell.it.utils;

import com.dwell.it.enums.WebPageDataSourceEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputOutputUtils {

    // Refer from https://stackoverflow.com/questions/18591242/java-extract-date-from-string-using-regex-failing
    private static final String dateFormatYYMMDDRegEx = "(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])";


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



    /**
     * 在一串文本中获取文本中所有的的时间、日期
     * @param inputText 输入字符串
     * @return String[] datetimes
     */
    public static String fetchDateTimeYYMMDDFromStringText(String inputText) {
        if (inputText == null || inputText.isEmpty())    return "";

        Matcher matcher = Pattern.compile(dateFormatYYMMDDRegEx).matcher(inputText);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            return inputText.substring(matchStart, matchEnd);
        }
        return "";
    }

}