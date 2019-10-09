package com.dwell.it.utils;

import com.dwell.it.entities.House;
import com.dwell.it.exception.InternalMethodInvokeException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FileInputOutputUtils {

    private static final String defaultExcelSheetName = "AMAP-SDK";

    /**
     * 使用IO流打印出所有的一级页面上的详情URL 并保存在文件中（二级页面的URL)，文件名：fetched_url.txt
     *
     * @param fileName   filePath
     * @param collection collection-sets
     */
    public static void saveContentToFiles(String fileName, Collection collection) throws InternalMethodInvokeException {
        if (fileName == null || fileName.length() == 0) return;

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


    /**
     * 读取本地txt文件，并将每一行的数据 添加到List中
     *
     * @return List<String> urlList
     */
    public static List<String> fetchedEachLineOfContentFromTextFile(String fileName) throws InternalMethodInvokeException {
        if (fileName == null || fileName.length() == 0) return new ArrayList<>();

        String content = readingFileWithBufferedReader(fileName);
        String[] seedsList = content.split("\n");
        return Arrays.asList(seedsList);
    }


    /**
     * 读取本地filepath的文件 并将读取内容以文本形式返回
     *
     * @param filePath 文件路径
     * @return 文本内容
     */
    public static String readingFileWithBufferedReader(String filePath) throws InternalMethodInvokeException {
        if (filePath == null || filePath.isEmpty()) return "";

        StringBuilder contentBuilder = new StringBuilder();
        String result;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException ex) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            throw new InternalMethodInvokeException(InternalMethodInvokeException.INTERNAL_METHOD_INVOKE_PREFIX + "" + runningMethodName + "()");
        } finally {
            result = contentBuilder.toString();
        }
        return result.length() == 0 ? "" : result;
    }


    /**
     * 根据已有数据 创建新的excel文件
     *
     * @param filePath 文件名
     * @param dataList 数据源
     */
    public static boolean createExcelFileFromDatasource(String filePath, List<House> dataList) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(defaultExcelSheetName);

        Row headerRow = sheet.createRow(0);
        String[] titleColumns = new String[]{"编号", "小区名称", "房租价格", "经纬度"};
        for (int i = 0; i < titleColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titleColumns[i]);
        }

        int rowNum = 1;
        for (House house : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(house.getId() + "");
            row.createCell(1).setCellValue(house.getCityZone());
            row.createCell(2).setCellValue(house.getTradePrice());
            row.createCell(3).setCellValue(house.getGeoInfo());
        }

        for (int i = 0; i < titleColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = null;
        try {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fileOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                workbook.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
