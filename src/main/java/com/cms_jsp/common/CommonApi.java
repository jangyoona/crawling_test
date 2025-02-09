package com.cms_jsp.common;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonApi {
	
//	@Autowired
//	private WebClient dataWebClient;
	
//	private WebClient bizNumWebClient;

	@Autowired
	private Map<String, WebClient> webClientMap;
	
	//data GET 방식
	public JSONObject dataGetApi(MultiValueMap<String, String> params, String surl, String clientName) {
		JSONObject jsonObjResult = new JSONObject();
		WebClient webClient = webClientMap.get(clientName);
		
		try {
			
			String result = webClient.get()
					.uri(uriBuilder -> uriBuilder
							.path(surl)
							.queryParams(params)
							.build())
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			log.debug(surl + "[result]::\n" + result);

			JSONObject jsonObj = CommUtil.stringToJSonObj(result);
		
			if (!jsonObj.isEmpty()) {
				JSONObject response = (JSONObject) jsonObj.get("response");
				JSONObject header;
				String resultCode;
				
				if (response != null) {
					header = (JSONObject) response.get("header");
				    resultCode = (String) header.get("resultCode");
				} else {
					header = (JSONObject) jsonObj.get("header");
				    resultCode = (String) header.get("resultCode");
				}
			
				if (!resultCode.equals("00")) {
					jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
		        	jsonObjResult.put("result", jsonObj);
					jsonObjResult.put("status", "01");
					jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
					return jsonObjResult;
				}
				jsonObjResult = CommUtil.messageJson("OK" , "정상적으로 조회되었습니다.");
		        jsonObjResult.put("result", jsonObj);
		        jsonObjResult.put("status", "00");
			} else {
				jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
				jsonObjResult.put("status", "01");
				jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
			}
			
			return jsonObjResult;
			
		} catch(Exception e) {
			
			log.error(surl + "[Exception]::" + e);
			return CommUtil.messageJson("FAIL" , "잠시 후 다시 사용하세요.");
		}
		
	}
	
	//보조금24, K-Startup 공용 GET 방식
	public JSONObject dataGetApiNew(MultiValueMap<String, String> params, String surl, String clientName) {
		JSONObject jsonObjResult = new JSONObject();
		WebClient webClient = webClientMap.get(clientName);
		
		try {
			
			String result = webClient.get()
					.uri(uriBuilder -> uriBuilder
							.path(surl)
							.queryParams(params)
							.build())
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			log.debug(surl + "[result]::\n" + result);

			JSONObject jsonObj = CommUtil.stringToJSonObj(result);
		
			if (!jsonObj.isEmpty()) {
				Long totalCount;
				
				if (jsonObj != null) {
					totalCount = (Long) jsonObj.get("totalCount");
				} else {
					totalCount = (Long) jsonObj.get("totalCount");
				}
			
				if (totalCount == 0) {
					jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
		        	jsonObjResult.put("result", jsonObj);
					jsonObjResult.put("status", "01");
					jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
					return jsonObjResult;
				}
				jsonObjResult = CommUtil.messageJson("OK" , "정상적으로 조회되었습니다.");
		        jsonObjResult.put("result", jsonObj);
		        jsonObjResult.put("status", "00");
			} else {
				jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
				jsonObjResult.put("status", "01");
				jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
			}
			
			return jsonObjResult;
			
		} catch(Exception e) {
			
			log.error(surl + "[Exception]::" + e);
			return CommUtil.messageJson("FAIL" , "잠시 후 다시 사용하세요.");
		}
		
	}
	
	public JSONObject dataGetApiYoonA(MultiValueMap<String, String> params, String surl, String clientName) {
		JSONObject jsonObjResult = new JSONObject();
		WebClient webClient = webClientMap.get(clientName);
		
		try {
			
			String result = webClient.get()
					.uri(uriBuilder -> uriBuilder
							.path(surl)
							.queryParams(params)
							.build())
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			log.debug(surl + "[result]::\n" + result);

			JSONObject jsonObj = CommUtil.stringToJSonObj(result);
		
			if (!jsonObj.isEmpty()) {
				Long totalCount;
				
				totalCount = (Long) jsonObj.get("totalCount");
			
				if (totalCount == 0) {
					jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
		        	jsonObjResult.put("result", jsonObj);
					jsonObjResult.put("status", "01");
					jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
					return jsonObjResult;
				}
				jsonObjResult = CommUtil.messageJson("OK" , "정상적으로 조회되었습니다.");
		        jsonObjResult.put("result", jsonObj);
		        jsonObjResult.put("status", "00");
			} else {
				jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
				jsonObjResult.put("status", "01");
				jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
			}
			
			return jsonObjResult;
			
		} catch(Exception e) {
			
			log.error(surl + "[Exception]::" + e);
			return CommUtil.messageJson("FAIL" , "잠시 후 다시 사용하세요.");
		}
		
	}
	
	
	//bizNum POST 방식
//	public JSONObject bizNumPostApi(String param, String surl) {
//		JSONObject jsonObjResult = new JSONObject();
//		
//		try {
//			
//			String sdata = "b_no=1248100998";
//			
//			String result = bizNumWebClient.post()
//					.uri(surl)
//					.contentType(MediaType.APPLICATION_JSON)
//					.body(BodyInserters.fromValue(sdata))
//					.retrieve()
//					.bodyToMono(String.class)
//					.block();
//			
//			log.debug(surl + "[result]::\n" + result);
//
//			JSONObject jsonObj = CommUtil.stringToJSonObj(result);
//		
//			if (!jsonObj.isEmpty()) {
//				JSONObject response = (JSONObject) jsonObj.get("response");
//				JSONObject header;
//				String resultCode;
//				
//				if (response != null) {
//					header = (JSONObject) response.get("header");
//				    resultCode = (String) header.get("resultCode");
//				} else {
//					header = (JSONObject) jsonObj.get("header");
//				    resultCode = (String) header.get("resultCode");
//				}
//			
//				if (!resultCode.equals("00")) {
//					jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
//		        	jsonObjResult.put("result", jsonObj);
//					jsonObjResult.put("status", "01");
//					jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
//					return jsonObjResult;
//				}
//				jsonObjResult = CommUtil.messageJson("OK" , "정상적으로 조회되었습니다.");
//		        jsonObjResult.put("result", jsonObj);
//		        jsonObjResult.put("status", "00");
//			} else {
//				jsonObjResult = CommUtil.messageJson("FAIL" , "정상적으로 조회되지 않았습니다.");
//				jsonObjResult.put("status", "01");
//				jsonObjResult.put("errorMessage", "정상적으로 조회되지 않았습니다.");
//			}
//			
//			return jsonObjResult;
//			
//		} catch(Exception e) {
//			
//			log.error(surl + "[Exception]::" + e);
//			return CommUtil.messageJson("FAIL" , "잠시 후 다시 사용하세요.");
//		}
//		
//	}
	
}
