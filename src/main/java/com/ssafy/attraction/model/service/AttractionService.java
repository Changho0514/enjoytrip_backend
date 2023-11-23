package com.ssafy.attraction.model.service;

import java.util.List;

import com.ssafy.attraction.model.AttractionDescriptionDto;
import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.GugunDto;
import com.ssafy.attraction.model.SidoDto;

public interface AttractionService {
	
	List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) throws Exception;
	List<GugunDto> gugunList(int sidoCode) throws Exception;
	List<SidoDto> sidoList() throws Exception;
	AttractionInfoDto getAttraction(int contentId) throws Exception;
	AttractionDescriptionDto getOverview(int contentId) throws Exception;
}
