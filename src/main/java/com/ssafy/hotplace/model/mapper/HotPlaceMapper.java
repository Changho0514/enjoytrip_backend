package com.ssafy.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.hotplace.model.HotPlaceListDto;
import com.ssafy.hotplace.model.HotPlaceRegisterDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hotplace.model.HotPlaceDto;

@Mapper
public interface HotPlaceMapper {
	void write(HotPlaceDto hotPlaceDto) throws SQLException;
	List<HotPlaceDto> hotplaceList(Map<String, Object> param) throws SQLException;
	int getTotalCount(Map<String, Object> param) throws SQLException;
	int getTotalCountWithJoin(Map<String, Object> param) throws SQLException;
	HotPlaceDto detail(int hotplaceNo) throws SQLException;
	void updateRecommendationCount(int hotplaceNo) throws SQLException;
	void addRecommendation(Map<String, Object> param) throws SQLException;
	void update(HotPlaceDto hotplaceDto) throws SQLException;
	void delete(int hotplaceNo) throws SQLException;
    void writeFile(Map<String, Object> params) throws SQLException;
    List<HotPlaceDto> hotplaceTOP3(String userId) throws SQLException;
	List<HotPlaceDto> recommendList(String userId) throws SQLException;
}
