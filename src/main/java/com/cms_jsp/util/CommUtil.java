package com.cms_jsp.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CommUtil {
	
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

}
