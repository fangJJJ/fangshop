package com.fang.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToPojoUtil {
	private static Logger logger = LogManager.getLogger(ExcelToPojoUtil.class);
	
	public static <T> List<T> toPojo(Class<T> type, InputStream inputStream) throws Exception{
		List<T> results = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		if(sheet == null) {
			throw new Exception("取得excel檔案失敗");
		}
		//取得表頭欄位名稱
		List<String> colNames = new ArrayList<>();
        Row headerRow = sheet.getRow(1);
        int cellNum = (headerRow!=null)?headerRow.getLastCellNum():0;
        for (int i = 0; i < cellNum; i++) {
            Cell headerCell = headerRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            colNames.add(headerCell != null ? headerCell.getStringCellValue() : null);
        }
        for (int j = 2; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if(row == null) {
            	continue;
            }
            try {
                T result = type.getDeclaredConstructor().newInstance();
                for (int k = 0; k < cellNum; k++) {
                    if (colNames.get(k) != null) {
                        Cell cell = row.getCell(k, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); 
                        if (cell != null) {
                            DataFormatter formatter = new DataFormatter();
                            String strValue = formatter.formatCellValue(cell);
                            Field field = type.getDeclaredField(colNames.get(k));
                            field.setAccessible(true);
                            if (field != null) {
                                Object value = null;
                                if (String.class.equals(field.getType())) {
                                	//format各種型態欄位的值為string
                                	DataFormatter dataFormatter = new DataFormatter();
                                	value = dataFormatter.formatCellValue(cell);
                                } else if (Integer.class.equals(field.getType())) {
                                    value = Integer.valueOf(strValue);
                                } else if (Timestamp.class.equals(field.getType())) {
                                	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                	LocalDateTime dateTime = LocalDateTime.parse(strValue, format);
                                    value = Timestamp.valueOf(dateTime);
                                } else {
                                    value = null;
                                } 
                                field.set(result, value);
                            }
                        }
                    }
                }
                results.add(result);
            } catch (Exception e) {
                logger.warn("toPojo fail", e);
            }
        }
        return results;
	}
}
