package com.cms_jsp.yoona;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cms_jsp.common.CommonApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiService {
	private final CommonApi commonApi;
	
	private static final String SERVICE_KEY = "7qxxddEIeehSOXh428wpKeAYv5Th%2Baa2oN2ZGU9w%2Bk%2BgoIAMjErnqxNWQQ99edckzNSx5b7KpAjwEDCUVqbIzg%3D%3D";
	
	
	/*
	 * 날씨 정보
	 */
	public JSONObject get1() {

		String uri = "/api/15124160/v1/uddi:6e2b6b95-3eb9-4c81-aee7-9a79fbb39b9b";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("serviceKey", SERVICE_KEY);
		params.add("page", "1");
		params.add("returnType", "json");
		
		return commonApi.dataGetApiNew(params, uri, "biz");
	}
	
	
	
	/*
	 * 행정안전부_공공서비스
	 */
	
	public JSONObject get2() {
		
		String uri= "/api/15126502/v1/uddi:5bcbbb59-913d-459f-be0f-ae89025b26b4";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("serviceKey", SERVICE_KEY);
		params.add("page", "1");
		params.add("perPage", "10");
		params.add("returnType", "json");
		
		return commonApi.dataGetApiNew(params, uri, "biz");
	}
	
	public JSONObject get3() {
		
		String uri= "/api/gov24/v3/serviceList";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("serviceKey", SERVICE_KEY);
		params.add("page", "1");
		params.add("perPage", "10");
		params.add("returnType", "json");
		
		return commonApi.dataGetApiNew(params, uri, "biz");
	}
	
	public JSONObject get4() {
		
		String uri= "/api/gov24/v3/serviceList";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("serviceKey", SERVICE_KEY);
		params.add("page", "1");
		params.add("perPage", "10");
		params.add("returnType", "json");
		
		return commonApi.dataGetApiNew(params, uri, "biz");
	}
	
	public JSONObject get5() {
		
		String uri= "/api/gov24/v3/supportConditions";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("serviceKey", SERVICE_KEY);
		params.add("page", "1");
		params.add("perPage", "10");
		params.add("returnType", "json");
		
		return commonApi.dataGetApiNew(params, uri, "biz");
	}


}
