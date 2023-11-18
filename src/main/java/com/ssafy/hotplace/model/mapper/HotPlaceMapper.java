package com.ssafy.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hotplace.model.HotPlaceDto;

@Mapper
public interface HotPlaceMapper {
	int getCount() throws SQLException;
	void write(HotPlaceDto hotPlaceDto) throws SQLException;
	void writeFile(Map<String, Object> params) throws SQLException;
	
	List<HotPlaceDto> hotplaceList(Map<String, Object> param) throws SQLException;
	int getTotalCount(Map<String, Object> param) throws SQLException;
	List<HotPlaceDto> hotplaceTOP3() throws SQLException;
	HotPlaceDto detail(int hotplaceNo) throws SQLException;
	
	void delete(int hotplaceNo) throws SQLException;
	void modify(HotPlaceDto hotplaceDto) throws SQLException;
	
	int getRecommend(int hotplaceNo) throws SQLException;

	void increaseRecommendationCount(int hotplaceNo) throws SQLException;
	void decreaseRecommendationCount(int hotplaceNo) throws SQLException;
//	List<HotPlaceDto> getRecommendList(String userId) throws Exception;
	
	
//	int getTotalCountWithJoin(Map<String, Object> param) throws SQLException;
//	void updateRecommendationCount(int hotplaceNo) throws SQLException;
//	void addRecommendation(Map<String, Object> param) throws SQLException;
}
