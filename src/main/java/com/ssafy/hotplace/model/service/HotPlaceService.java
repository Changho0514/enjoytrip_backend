package com.ssafy.hotplace.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hotplace.model.HotPlaceDto;
import com.ssafy.hotplace.model.HotPlaceListDto;
import com.ssafy.hotplace.model.HotPlaceParameterDto;

public interface HotPlaceService {
	void write(HotPlaceDto hotPlaceDto) throws Exception;
	void writeFile(Map<String, Object> params) throws Exception;
	
	HotPlaceListDto hotplaceList(HotPlaceParameterDto hotplaceParameterDto) throws Exception;
	List<HotPlaceDto> hotplaceTOP3() throws Exception;
	HotPlaceDto detail(int hotplaceNo) throws Exception;
	
	void delete(int hotplaceNo) throws Exception;
	void modify(HotPlaceDto hotplaceDto) throws Exception;
	
//	void recommend(int hotplaceNo, String userId) throws Exception;
	List<Integer> getMyRecommend(String userId) throws Exception;
	int getRecommendCount(int hotplaceNo) throws Exception;

	void changeRecommendState(int hotplaceNo, String userId) throws Exception;
	List<HotPlaceDto> getMyRecommendList(String userId) throws Exception;
}
