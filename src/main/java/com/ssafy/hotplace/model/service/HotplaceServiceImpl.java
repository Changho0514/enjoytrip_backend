package com.ssafy.hotplace.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.hotplace.model.HotPlaceListDto;
import com.ssafy.hotplace.model.HotPlaceParameterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.hotplace.model.HotPlaceDto;
import com.ssafy.hotplace.model.mapper.HotPlaceMapper;
//import com.ssafy.util.PageNavigation;
//import com.ssafy.util.SizeConstant;

@Service
public class HotPlaceServiceImpl implements IHotPlaceService {
	
	private HotPlaceMapper hotplaceMapper;

	public HotPlaceServiceImpl(HotPlaceMapper hotplaceMapper) {
		super();
		this.hotplaceMapper = hotplaceMapper;
	}

	@Override
	@Transactional
	public void write(HotPlaceDto hotplaceDto) throws Exception {
		hotplaceMapper.write(hotplaceDto);
	}

	@Override
	public com.ssafy.hotplace.model.HotPlaceListDto hotplaceList(HotPlaceParameterDto hotplaceParameterDto) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", hotplaceParameterDto.getWord()== null ? "" : hotplaceParameterDto.getWord());
		int currentPage = hotplaceParameterDto.getPgno();
		int sizePerPage = hotplaceParameterDto.getSpp();
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = hotplaceParameterDto.getKey();
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");

		List<HotPlaceDto> list = hotplaceMapper.hotplaceList(param);

		int totalArticleCount = hotplaceMapper.getTotalCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		HotPlaceListDto hotPlaceListDto = new HotPlaceListDto();
		hotPlaceListDto.setHotplaces(list);
		hotPlaceListDto.setCurrentPage(currentPage);
		hotPlaceListDto.setTotalPageCount(totalPageCount);

		return hotPlaceListDto;
	}

	//TODO : Pagination 부분
//	@Override
//	@Transactional(readOnly = true)
//	public List<HotPlaceDto> hotplaceList(Map<String, String> map) throws Exception {
//		Map<String, Object> param = new HashMap<String, Object>();
//		String key = map.get("key");
//		if("username".equals(key))
//			key = "name";
//		param.put("sort", map.get("sort"));
//		param.put("key", key == null ? "" : key);
//		param.put("word", map.get("word") == null ? "" : map.get("word"));
//		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
//		int start = pgNo * .HOT_PLACE_LIST_SIZE - SizeConstant.HOT_PLACE_LIST_SIZE;
//		param.put("start", start);
//		param.put("listsize", SizeConstant.HOT_PLACE_LIST_SIZE);
//		return hotplaceMapper.hotplaceList(param);
//	}

	@Override
	@Transactional(readOnly = true)
	public List<HotPlaceDto> getRecommendList(String userId) throws Exception {
		List<HotPlaceDto> recList = new ArrayList<>();
		List<HotPlaceDto> noList = hotplaceMapper.recommendList(userId); // 좋아요 누른 목록 key 받아오기
		for(HotPlaceDto h : noList){
			recList.add(hotplaceMapper.detail(h.getHotplaceNo()));
		}
		return recList;
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
	public void update(HotPlaceDto hotplaceDto) throws Exception {
		hotplaceMapper.update(hotplaceDto);
	}

	@Override
	@Transactional
	public void recommend(int hotplaceNo, String userId) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("hotplaceNo", hotplaceNo);
		param.put("userId", userId);
		hotplaceMapper.addRecommendation(param);
		hotplaceMapper.updateRecommendationCount(hotplaceNo);
	}

	@Override
	public void writeFile(Map<String, Object> params) throws Exception{
		hotplaceMapper.writeFile(params);
	}

}
