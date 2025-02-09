package com.cms_jsp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cms_jsp.model.ZValue;

@Mapper
public interface CrawlingMapper {
	
	int insertNotice(List<ZValue> param);
	
	List<Long> findTop10BoardNos();

}
