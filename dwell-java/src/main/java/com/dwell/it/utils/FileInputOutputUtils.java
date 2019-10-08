package com.dwell.it.utils;

import com.dwell.it.exception.InternalMethodInvokeException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;

public class FileInputOutputUtils {


    /**
     * 使用IO流打印出所有的一级页面上的详情URL 并保存在文件中（二级页面的URL)，文件名：fetched_url.txt
     * @param fileName   filePath
     * @param collection collection-sets
     */
    public static void saveContentToFiles(String fileName, Collection collection) throws InternalMethodInvokeException {
        if (fileName == null || fileName.length() == 0)    return;

        PrintStream previousOut = System.out;
        try {
            String outputFileName = TextInputOutputUtils.safeTextContent(fileName);
            PrintStream out = new PrintStream(new FileOutputStream(outputFileName));
            System.setOut(out);
            for (Object valueObject : collection) {
                System.out.println(valueObject.toString());
            }
        } catch (FileNotFoundException ex) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            throw new InternalMethodInvokeException(InternalMethodInvokeException.INTERNAL_METHOD_INVOKE_PREFIX + "" + runningMethodName + "()");
        } finally {
            System.setOut(previousOut);
        }
    }
}
