package com.cms_jsp.common;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommUtil {
	
	/**
	 * 날짜문자를 날짜형식으로 변환
	 * @param sdate
	 * @param itype
	 * @return
	 */
	public static String getStringTodate(String sdate, int itype) {
		String result = "";
		Date nowDate = new Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simpleDateFormat = null;
		if (itype == 0) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} else if (itype == 1) {
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		} else if (itype == 2) {
			simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		} else {
			simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
		}
		
		try {
			Date formatDate = dtFormat.parse(sdate);
			result = simpleDateFormat.format(formatDate);
		} catch(Exception e) {
			result = simpleDateFormat.format(nowDate);
			log.info("getStringTodate()=>" + "Exception:: [" + e + "]");
		}
		
		return result;
	}
	
	/**
	 * 시간문자를 시간형식을 변환
	 * @param stime
	 * @return
	 */
	public static String getStringTotime(String stime) {
		DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("HHmm");
	    LocalTime time = LocalTime.parse(stime, inFormat);
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(outFormat).toString();
	}
	
	/**
	 * 날짜형식 문자를 요일 변환
	 * @param sday
	 * @param stype : 1-토, 2-토, 3-토요일
	 * @return
	 */
	public static String getStringToWeek(String sday, int itype) {
		String result = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
    	LocalDate date = LocalDate.parse(sday, formatter);
    	DayOfWeek dayOfWeek = date.getDayOfWeek();
    	if (itype == 1) {
    		result = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    	} else if (itype == 1) {
    		result = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);;
    	} else {
    		result = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    	}
    	return result;
	}
	
	/**
	 * 한글로만 이루어진 문자열인지 검사
	 * @param mberNm
	 * @return
	 */
	public static boolean isOnlyHangul(String mberNm) {
		if (mberNm == null) {
			return false;
		}
		return mberNm.matches("^[ㄱ-하-ㅣ가-힣]+$");
	}
	
	/**
	 * 랜덤하게 0~z까지 7자리
	 * @return
	 */
	public static String getRandomize() {
		int leftLimit = 48;   // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 7;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		return generatedString;
	}
	
	/**
	 * String To JSON
	 * @param str
	 * @return
	 */
	public static JSONObject getStrToJson(String str) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(str);
	        //logger.debug("Json=>" + jsonObject.get("access_token"));
	        return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 날짜포맷
	 * @param itag
	 * @return
	 */
	public static String getTimeStamp(int itag) {
		String result = "";
		if (itag == 0) {
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			result = date.format(new Date());
		} else if (itag == 1) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMMdd");
			result = dYyyymmdd.format(new Date());
		} else if (itag == 2) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMMddHHmm");
			result = dYyyymmdd.format(new Date());
		} else if (itag == 3) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMM");
			result = dYyyymmdd.format(new Date());
		} else if (itag == 4) {
			result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
		}
		
		return result;
	}
	
	/**
	 * Message
	 * @param code
	 * @param message
	 * @return
	 */
	public static JSONObject messageJson(String code, String message) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("CODE", code);
		jsonObj.put("MESSAGE", message);
		return jsonObj;
	}
	
	/**
	 * Message
	 * @param code
	 * @param message
	 * @return
	 */
	public static JSONObject messageJsonSe(String code, String message, String data) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("CODE", code);
		jsonObj.put("MESSAGE", message);
		jsonObj.put("DATA", data);
		return jsonObj;
	}
	
	/**
	 * String to JSONObject
	 * @param jsonstr
	 * @return
	 */
	public static JSONObject stringToJSonObj(String jsonstr) {
		JSONObject jsonObj = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
	        Object obj = jsonParser.parse(jsonstr);
	        jsonObj = (JSONObject)obj;
			return jsonObj;
		} catch (Exception e) {
			return jsonObj;
		}
	}
	
	/**
	 * 
	 * @param string
	 * @param tagname
	 * @param replacement
	 * @return
	 */
	public static String replaceTag(String string, String tagname, String replacement) throws Exception {
		String result = new String(string);

		try {
			String startTag = "<" + tagname + ">";
			String endTag = "</" + tagname + ">";

			int startPosition = result.indexOf(startTag);
			int endPosition = result.indexOf(endTag);

			String target = result.substring(startPosition, endPosition + endTag.length());

			result = result.replace(target, replacement);
		} catch (Exception e) {
			log.error("replaceTag::" + e.getMessage());
		}

		return result;
	}
	
	/**
	 * Outbound Port 매핑
	 * @param xroshotServerIP
	 * @return
	 */
	public static Map<String, String> getOutboundPort(String xroshotServerIP){
		String[] array = xroshotServerIP.split(",");
		Map<String, String> map = new HashMap<>();
		
		for(int i=0;i<array.length;i++) {
			String[] subArray = array[i].split(":");
			map.put(subArray[0], subArray[1]);
		}
		return map;
	}
	
	/**
     * 8자리 난수 메시지ID생성
     * @param ilen : 난수의 길이
     */
	public static String genRanmMsgId(int ilen) {
        Random randm = new Random();
        String result = "";
        
        for(int i=0; i<ilen; i++) {
            String randomi = Integer.toString(randm.nextInt(10)); //0~9 난수발생
            result += randomi;
        }
        return result;
    }

}
