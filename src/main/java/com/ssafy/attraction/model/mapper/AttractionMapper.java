package com.ssafy.attraction.model.mapper;

import java.sql.SQLException;

// com.ssafy.attraction.model.mapper.AttractionMapper

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.GugunDto;
import com.ssafy.attraction.model.SidoDto;

@Mapper
public interface AttractionMapper {
	
	List<AttractionInfoDto> attractionList(Map<String, Object> map) throws SQLException;
	List<GugunDto> gugunList(int sidoCode) throws SQLException;
	List<SidoDto> sidoList() throws SQLException;
	AttractionInfoDto getAttraction(int contentId) throws SQLException;
}
