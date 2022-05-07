package com.fang.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/**
     * 手機檢核防呆
     * 手機：「開頭為09」+「10位數(不可多於或少於)」+「必為數字」
     * @param mobile
     * @return errorMsg
     */
    public static String checkMobile(String mobile){
    	String errorMsg = "";
    	if(!mobile.startsWith("09")){
    		errorMsg = "手機開頭須為09！例：0912345678";
    	}else if(mobile.length() != 10){
    		errorMsg = "手機長度須為10個數字";
    	}else if(!mobile.matches("^[0-9]*$")) {
    		errorMsg = "手機須全部為數字！";
    	}
    	return errorMsg;
    }
    
    /**
     * Email檢核防呆
     * @param email
     * @return errorMsg
     */
    public static String checkEmail(String email){
    	String errorMsg = "";
		if (email.indexOf("..") != -1){
			errorMsg = "Email不能有連續兩個【.】以上之符號(如:ab..cde@domain.com)";	
		}
		if (email.substring(0, email.indexOf("@")+1).split("\\.").length > 4){
			errorMsg = "Email不能有四個(含)【.】以上之符號(如:a.b.c.d.e@domain.com)";
		}
		if (email.indexOf("+") != -1 && email.indexOf("@gmail.com") != -1){
			errorMsg = "Gmail不能有【+】符號(如:ab+cde@gmail.com)";
		}
		//Java email validation permitted by RFC 5322
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			errorMsg = "Email格式錯誤，請重新輸入(如:user@domain.com)";
		}
		return errorMsg;
    }
    
    /**
     * 擷取目前系統時間(YYYY-MM-DD 00:00:00.00)
     * @return java.sql.Timestamp
     */
	public Timestamp getCurrentTimestamp() {
		Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        return timestamp;
	}
	
	/**
     * 擷取今天日期(yyyyMMdd)
     * @return String
     */
	public static String getToday() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	/**
	 * 利用亂數產生密碼
	 * i_num:密碼位數
	 * @return strPswd
	 */
	public static String makPswd(int i_num) {
		String strLetter = "23456789abcdefghijknmpqrstuvwxyz";
		StringBuffer strPswd = new StringBuffer();
		int k = 0;
		for (int i = 1; i <= i_num; i++) {
			k = Math.round(Math.round(Math.random() * 1000) % 32);
			strPswd = strPswd.append(strLetter.charAt(k));
		}
		return strPswd.toString();
	}
	
	/**
	 * 是否為數字
	 * @param String
	 * @return boolean
	 */
	public static boolean isNumeric(String str){
		return str != null && str.matches("[0-9.]+");
    }
	
	/**
	 * 從左邊補足字元
	 * @param i_source : 原始字串
	 * @param i_len : 欲補足之長度
	 * @param i_str : 欲補之字元
	 * @return String : 結果字串
	 */
	public static String lpad(String i_source, int i_len, String i_str) {
		StringBuffer strResult = new StringBuffer(i_source);
		strResult.reverse();
		int intOffset = i_len - i_source.length();
		for (int i = 0; i < intOffset; i++) {
			strResult.append(i_str);
		}
		return strResult.reverse().toString();
	}
	
	/**
	 * 日期和時間兩個字串合併(date+time)轉為Timestamp
	 * @param date
	 * @param time
	 * @return Timestamp
	 * @throws ParseException
	 */
	public static Timestamp convertStringToTimestamp(String date, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDateTime = date+" "+time+":00";
		Date datetime = format.parse(strDateTime);
	    Timestamp timestamp = new Timestamp(datetime.getTime());
		return timestamp;
	}
	
	/**
	 * 字串轉為Timestamp
	 * @param date
	 * @param time
	 * @return Timestamp
	 * @throws ParseException
	 */
	public static Timestamp convertStringToTimestamp(String datetime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	LocalDateTime dateTime = LocalDateTime.parse(datetime, format);
        return Timestamp.valueOf(dateTime);
	}
}
