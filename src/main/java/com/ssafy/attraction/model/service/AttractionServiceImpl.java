package com.ssafy.attraction.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.GugunDto;
import com.ssafy.attraction.model.SidoDto;
import com.ssafy.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
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
		map.put("title", title==""?"":title);
		
		return attractionMapper.attractionList(map);
	}

	@Override
	public List<GugunDto> gugunList(int sidoCode) throws Exception {
		return attractionMapper.gugunList(sidoCode);
	}

	@Override
	public List<SidoDto> sidoList() throws Exception {
		return attractionMapper.sidoList();
	}

}
