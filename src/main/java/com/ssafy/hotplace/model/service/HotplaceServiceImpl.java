package com.ssafy.hotplace.model.service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.ssafy.hotplace.model.HotPlaceListDto;
import com.ssafy.hotplace.model.HotPlaceParameterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hotplace.model.HotPlaceDto;
import com.ssafy.hotplace.model.mapper.HotPlaceMapper;


@Service
public class HotPlaceServiceImpl implements HotPlaceService {
	
	private HotPlaceMapper hotplaceMapper;

	public HotPlaceServiceImpl(HotPlaceMapper hotplaceMapper) {
		super();
		this.hotplaceMapper = hotplaceMapper;
	}

	@Override
	@Transactional
	public void write(HotPlaceDto hotPlaceDto) throws Exception {
		hotplaceMapper.write(hotPlaceDto);
	}
	
	@Override
	public void writeFile(Map<String, Object> params) throws Exception{
		hotplaceMapper.writeFile(params);
	}

	@Override
	public HotPlaceListDto hotplaceList(HotPlaceParameterDto hotplaceParameterDto) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", hotplaceParameterDto.getWord()== null ? "" : hotplaceParameterDto.getWord());
		int currentPage = hotplaceParameterDto.getPgno();
		int sizePerPage = hotplaceParameterDto.getSpp();
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = hotplaceParameterDto.getKey();
		param.put("key", key == null ? "" : key);
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "b.user_id");

		List<HotPlaceDto> list = hotplaceMapper.hotplaceList(param);

		int totalArticleCount = hotplaceMapper.getTotalCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		HotPlaceListDto hotPlaceListDto = new HotPlaceListDto();
		hotPlaceListDto.setHotplaces(list);
		hotPlaceListDto.setCurrentPage(currentPage);
		hotPlaceListDto.setTotalPageCount(totalPageCount);

		return hotPlaceListDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HotPlaceDto> hotplaceTOP3() throws Exception {
		return hotplaceMapper.hotplaceTOP3();
	}

	@Override
	@Transactional(readOnly = true)
	public HotPlaceDto detail(int hotplaceNo) throws Exception {
		return hotplaceMapper.detail(hotplaceNo);
	}
	
	@Override
	@Transactional
	public void delete(int hotplaceNo) throws Exception {
		hotplaceMapper.delete(hotplaceNo);
	}
	
	@Override
	@Transactional
	public void modify(HotPlaceDto hotplaceDto) throws Exception {
		hotplaceMapper.modify(hotplaceDto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getRecommendCount(int hotplaceNo) throws Exception {
		return hotplaceMapper.getRecommendCount(hotplaceNo);
	}

	@Override
	public void changeRecommendState(int hotplaceNo, String userId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("hotplaceNo", hotplaceNo);
		// 핫플 누른 수가 없으면
		if(hotplaceMapper.checkRecommendation(param) == 0){
			hotplaceMapper.increaseRecommendationCount(hotplaceNo);
			hotplaceMapper.insertRecommendationHotplace(param);
		} else{
			hotplaceMapper.decreaseRecommendationCount(hotplaceNo);
			hotplaceMapper.deleteRecommendationHotplace(param);
		}
	}

	@Override
	public List<HotPlaceDto> getMyRecommendList(String userId) throws Exception {
		List<Integer> list = hotplaceMapper.getMyRecommendList(userId);
		List<HotPlaceDto> result = new ArrayList<>();

		for (int hotplaceNo: list) {
			HotPlaceDto dto = hotplaceMapper.detail(hotplaceNo);
			result.add(dto);
		}
		return result;
	}

//	@Override
//	@Transactional
//	public void recommend(int hotplaceNo, String userId) throws Exception {
//		Map<String, Object> param = new HashMap<>();
//		param.put("hotplaceNo", hotplaceNo);
//		param.put("userId", userId);
//		hotplaceMapper.addRecommendation(param);
//		hotplaceMapper.updateRecommendationCount(hotplaceNo);
//	}

}
