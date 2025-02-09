package com.cms_jsp.yoona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiContoroller {
	
	
	private final ApiService apiService;
	
	@GetMapping("/one")
	@ResponseBody
	public JSONArray get1() throws ParseException {
		
		JSONObject jsonData = apiService.get1();
		
		
        // JSON 객체에서 데이터 추출
		JSONObject dataJson = (JSONObject) jsonData.get("result");
		
		// 배열 꺼내기
		JSONArray jsonArray = (JSONArray) dataJson.get("data");
		
		
		List<HashMap<String, Object>> result = new ArrayList<>();
		for (Object object : jsonArray) {

			JSONObject last = (JSONObject) object;
			
			System.out.println(last.get("일자"));
			System.out.println(last.get("시간"));
			System.out.println(last.get("날씨"));
			
			HashMap<String, Object> map = new HashMap<>();
			map.put("시간", last.get("시간"));
			map.put("일자", last.get("일자"));
			map.put("날씨", last.get("날씨"));
			
			result.add(map);
		}
		
		for (HashMap<String, Object> hashMap : result) {
			System.out.println(hashMap.get("일자"));
			System.out.println(hashMap.get("시간") + "시");
			System.out.println(hashMap.get("날씨"));
			System.out.println("------------------------------");
		}
		
		return null;
//		return apiService.get1();
	}
	
	@GetMapping("/two")
	@ResponseBody
	public JSONObject get2() {
		JSONObject result = apiService.get2();
		return result;
	}
	
	@GetMapping("/three")
	@ResponseBody
	public JSONObject get3() {
		JSONObject result = apiService.get3();
		return result;
	}
	
	@GetMapping("/four")
	@ResponseBody
	public JSONObject get4() {
		JSONObject result = apiService.get4();
		return result;
	}
	
	@GetMapping("/five")
	@ResponseBody
	public JSONObject get5() {
		JSONObject result = apiService.get5();
		return result;
	}
	
	@GetMapping("/crawl")
	public String crawl(Model model) throws Exception {
		
		// 기본 크롤링 예시
//		Document doc = Jsoup.connect("https://news.naver.com/section/102").get();
//		System.out.println("문서 제목: " + doc.title()); // 탭 상단 title
//		
//		Elements target = doc.select(".Nlnb_menu_list a span"); // .Nlnb_menu_list > a > span 요소 가져오기
//		System.out.println(target);
//		System.out.println("----------------------------------");
//		String[] str = target.text().split(" ");
//		
//		for (String string : str) {
//			System.out.println(string);
//		}
		
		// 네이버 뉴스 한 섹션 뜯어오기
		Document doc = Jsoup.connect("https://news.naver.com/section/102")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.36 Safari/537.36")
				.get();
		Elements target = doc.select(".section_component.as_section_headline._PERSIST_CONTENT");
		System.out.println(target);
		model.addAttribute("html", target);
		
		
		
		return "crawl";
	}


}
