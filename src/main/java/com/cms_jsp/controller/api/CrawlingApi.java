package com.cms_jsp.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms_jsp.service.CrawlingService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class CrawlingApi {
	
	private final CrawlingService crawlingService;
	

	
	
	/*
	 * 소상공인시장진흥공단_공지사항 - 최초 저장: 전체 데이터 크롤링 및 저장 Service 에서 -> 저장활 페이지 갯수 설정
	 */
	@GetMapping("/1")
	public String crawl1() {
		return crawlingService.insertNotice();
		
	}
	


	/*
	 * 소상공인시장진흥공단_공지사항 - (스케쥴링) 새로운 공지사항 있는지 체크
	 */
	@GetMapping("/2")
	public String crawl2() {
		
		return crawlingService.checkForNewNotices();
	}

}
