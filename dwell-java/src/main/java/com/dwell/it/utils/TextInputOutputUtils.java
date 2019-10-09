package com.dwell.it.utils;

import com.dwell.it.enums.WebPageDataSourceEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputOutputUtils {

    // Refer from https://stackoverflow.com/questions/18591242/java-extract-date-from-string-using-regex-failing
    private static final String dateFormatYYMMDDRegEx = "(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])";

    // Refer from https://stackoverflow.com/questions/5713558/detect-and-extract-url-from-a-string
    // Pattern for recognizing a URL, based off RFC 3986
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*);",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


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


    /**
     * 将List<String> 转化为 String[]
     * @param list List<String>
     * @return String[]
     */
    public static String[] convertStringListToStringArray(List<String> list) {
        if (list == null || list.isEmpty())    return new String[] {""};

        int stringTypeObjectCount = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                stringTypeObjectCount++;
            }
        }
        return (stringTypeObjectCount == list.size()) ? list.toArray(new String[list.size()]) : new String[] {""};
    }


    /**
     * 获取文本中的一条URL链接 一直到文本的结尾， 并返回该条url链接
     * @param inputText 输入字符串
     * @return url
     */
    public static String detectAndExtractFirstUrlToTheEnd(String inputText) {
        if (inputText == null || inputText.isEmpty())    return "";

        Matcher matcher = urlPattern.matcher(inputText);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            return inputText.substring(matchStart, matchEnd);
        }
        return "";
    }


    /**
     * 移除String[]中所有的空租字符串元素（""）
     * @param inputList 字符串数组
     * @return 移除空字符串后的String[]
     */
    public static String[] removeEmptyElementFromStringArray(String[] inputList) {
        if (inputList == null || inputList.length == 0)    return new String[] {};

        List<String> list = new LinkedList<>();
        for (String item : inputList) {
            if (item.length() > 0) {
                list.add(item);
            }
        }
        return list.toArray(new String[list.size()]);
    }

}