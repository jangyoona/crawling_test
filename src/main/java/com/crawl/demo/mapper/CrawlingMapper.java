package com.crawl.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.demo.model.ZValue;

@Mapper
public interface CrawlingMapper {
	
	int insertNotice(List<ZValue> param);
	
	List<Long> findTop10BoardNos();

}
