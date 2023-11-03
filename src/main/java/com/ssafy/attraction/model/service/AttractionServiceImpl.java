package com.ssafy.attraction.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private SqlSession sqlSession;

	public AttractionServiceImpl(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	@Override
	public List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int sidocode = attractionInfoDto.getSidoCode();
		map.put("sidoCode", sidocode);
		int guguncode = attractionInfoDto.getGugunCode();
		map.put("gugunCode", guguncode);
		int contentTypeId = attractionInfoDto.getContentTypeId();
		map.put("contentTypeId", contentTypeId);
		String title = attractionInfoDto.getTitle();
		System.out.println("##### title : "+title);
		map.put("title", title==""?"":title);
		
		AttractionMapper attractionMapper = sqlSession.getMapper(AttractionMapper.class);
		return attractionMapper.attractionList(map);
	}

}
