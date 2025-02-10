package com.crawl.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crawl.demo.mapper.CrawlingMapper;
import com.crawl.demo.model.ZValue;
import com.crawl.demo.service.CrawlingService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingServiceImpl implements CrawlingService {
	
	private final CrawlingMapper crawlingMapper;

	/*
	 * 소상공인시장진흥공단 - 공지사항 URL
	 */
	private static final String NOTICE_BASE_URL = "https://www.semas.or.kr/web/board";
	private static final String NOTICE_PATH = "/webBoardList.kmdc?bCd=1&pNm=BOA0101&page=";
	
	
	/*
	 * 초기 공지사항 전체 저장용 (최초 1회만 실행)
	 * pagesToSave = 몇 페이지 가져올건지 설정 후 실행
	 */
	@Override
//	@PostConstruct
	public String insertNotice() {
		
		// 몇 페이지 크롤링 할건지 설정
		int pagesToSave = 50;
		int result = 0;
		
		// 크롤링 And DB 저장
		List<ZValue> crawledValues = crawlNotices(pagesToSave);
		result = crawlingMapper.insertNotice(crawledValues);
		
		// List<Entity> savedEntities = noticeRepository.saveAll(crawledValues);
		// result = savedEntities.size();
		
		log.debug("insertNotice()=>" + "DB 저장 데이터_ 총 [" + result + "건]");
		return String.format("DB 저장 데이터_ 총 [%d건]", result);
		
	}
	
	/*
	 *	소상공인시장진흥공단 - 공지사항 크롤링
	 */
	private List<ZValue> crawlNotices(int pagesToSave) {
		
		int page = 1;
		List<ZValue> list = new ArrayList<>();
		
		while (page <= pagesToSave) {
			
			try {
				Document doc = Jsoup.connect(NOTICE_BASE_URL + NOTICE_PATH + page).get();
				Elements trs = doc.select("table tbody tr");
				
				for (Element tr : trs) {      
					String boardUrl = NOTICE_BASE_URL + "/" + tr.select("td:eq(1) a").attr("href");
					
//					// 디테일 페이지 By.공지사항 내용, 이미지
					Map<String, String> detail = crawlNoticeDetails(boardUrl);
					
					// DB 저장 --> 이 부분과 메서드 반환 타입을 Entity 로 바꿔서 저장.
					ZValue zvl = new ZValue();
					zvl.put("boardNo",		tr.select("td:eq(0)").text());		// 게시글 번호 _일반 컬럼으로 지정 시 UNIQ 설정 필요
					zvl.put("title",		tr.select("td:eq(1) a").text());	// 제목
					zvl.put("writer",		tr.select("td:eq(3)").text()); 		// 작성자 
					zvl.put("content",		detail.get("content")); 			// 게시글 내용
					zvl.put("image",		detail.get("image")); 				// 게시글 이미지 (글 대체로 사용되는 경우가 있음) 
					zvl.put("detailUrl", 	boardUrl); 							// 게시글 url
					zvl.put("createdAt",	tr.select("td:eq(4)").text()); 		// 작성일
					zvl.put("hits",			tr.select("td:eq(5)").text()); 		// 조회수
	
					list.add(zvl);
				}
			} catch (Exception e) {
				e.printStackTrace();
//				log.debug("crawlNotices()=>" + "Exception:: [" + e + "]");
			}
			page++;
		}
		return list;
	}
	
	
	/*
	 *	소상공인시장진흥공단 - 공지사항 상세내용 크롤링 (내용, image 유효성 검사 및 추출)
	 */
	private Map<String, String> crawlNoticeDetails(String boardUrl) throws Exception {
		
		// 공지사항 내용 자체를 이미지로 대체하는 경우가 종종 있음. 게시글 내용이 null 일 경우 공지사항 url만 첨부해서 redirect 할건지,
		// 이미지 다운로드 해서 노출시킬 건지 선택해야 될 듯? 그럼 첨부파일도 다운해야 될거 같은데.. 흠..
		Elements doc = Jsoup.connect(boardUrl).get().select("table tbody tr");
		String content = "";
		
		/*
		 * 참고로
		 * 첨부파일 있으면 eq(2)가 첨부파일 / eq(3)이 컨텐츠
		 * 첨부파일 없으면 eq(2)가 컨텐츠
		 */
		
		// 첨부파일 유무 And 공지사항 내용 빈값 체크
		// 게시글 내용을 텍스트만이 아니라, HTML 전체를 가져와야 할 듯...
		if(doc.select("tr:eq(2)").text().contains("첨부")) {
			content = doc.select("tr:eq(3)").isEmpty() ? null : doc.select("tr:eq(3)").text();
		} else {
			content = doc.select("tr:eq(2)").isEmpty() ? null : doc.select("tr:eq(2)").text();
		}

		
		// 이미지 유효성 검사
		String isImage = doc.select("img").attr("src");
		String image = "";
		
		if(isImage.equals("")) {
			image = null;
		} else {
			// 이미지 다운로드할거면 - 로직 짜야됨 지금은 임시
			image = "이미지 있음";
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("content", content);
		map.put("image", image);
		
		return map;
	}


	
	/*
	 *	소상공인시장진흥공단 - 공지사항 (스케줄러용) 새로운 공지사항 확인 및 저장
	 */
//	@Scheduled(cron = "0 0 0  * * *")
	@Transactional
	@Override
	public String checkForNewNotices() {
		
		int pagesToCheck = 1; // 일단 1페이지로 지정했음 추 후 바꿔도 무방함 단, 아래 최근 데이터 가져오는 갯수도 맞춰야 할 듯
		int result = 0;
		
		// 크롤링
		List<ZValue> crawledValues = crawlNotices(pagesToCheck);
		
		// 최근 10개 데이터 조회
		List<Long> existingBoardNos = crawlingMapper.findTop10BoardNos();
//		List<Long> findTop10ByOrderByBoardNoDesc();
		
		// 최근 10개 bardNo 와 크롤링한 boardNo 비교하여 새로운 게시글만 저장
		List<ZValue> newNotices = crawledValues.stream()
			.filter(item -> !existingBoardNos.contains(Long.valueOf(item.get("boardNo").toString())))
			.collect(Collectors.toList());
		
		
		try {
			if(!newNotices.isEmpty()) {
				result = crawlingMapper.insertNotice(newNotices);
//				List<Entity> savedEntities = noticeRepository.saveAll(entities);
//				result = savedEntities.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("checkForNewNotices()=>" + "Exception:: [" + "Error Crawling_save:" + e + "]");
		}

		log.info("checkForNewNotices()=>" + "[" + "소상공인시장진흥공단_공지사항 크롤링 결과: " + result + "건 수집]");
		return String.format("DB 저장 데이터_ 총 [%d건]", result);
	}
	
	
	
	
}
