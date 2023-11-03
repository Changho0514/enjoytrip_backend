package com.ssafy.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.AttractionInfoDto;

public interface AttractionService {
	
	List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) throws Exception;

}
