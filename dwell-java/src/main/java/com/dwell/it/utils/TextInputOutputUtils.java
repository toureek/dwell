package com.dwell.it.utils;

public class TextInputOutputUtils {

    /**
     * 安全文本
     * @param inputText 入参文本
     * @return 安全文本
     */
    public static String safeTextContent(String inputText) {
        return inputText.length() > 0 ? inputText : "";
    }
}