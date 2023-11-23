package com.ssafy.mytrip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mytrip.model.MytripDto;
import com.ssafy.mytrip.model.MytripInfoDto;
import com.ssafy.mytrip.model.mapper.MytripMapper;

@Service
public class MytripServiceImpl implements MytripService {
	
	private MytripMapper mytripMapper;

	public MytripServiceImpl(MytripMapper mytripMapper) {
		super();
		this.mytripMapper = mytripMapper;
	}

	@Override
	public List<MytripDto> getMytrip(MytripDto mytripDto) throws Exception {
		return mytripMapper.getMytrip(mytripDto);
	}

	@Override
	public void addMytrip(MytripDto mytripDto) throws Exception {
		mytripMapper.addMytrip(mytripDto);
	}

	@Override
	public int getMytripMax(String userId) throws Exception {
		return mytripMapper.getMytripMax(userId);
	}

	@Override
	public List<Integer> getMytripAll(String userId) throws Exception {
		return mytripMapper.getMytripAll(userId);
	}

	@Override
	public void deleteMytripAll(MytripDto mytripDto) throws Exception {
		mytripMapper.deleteMytripAll(mytripDto);
	}

	@Override
	public void deleteMytrip(int no) throws Exception {
		mytripMapper.deleteMytrip(no);
	}

	@Override
	public int getMytripCount(String userId) throws Exception {
		return mytripMapper.getMytripCount(userId);
	}

	@Override
	@Transactional
	public void addMytripAll(MytripDto[] list) throws Exception {
		for (int i = 0; i < list.length; i++) {
			mytripMapper.addMytrip(list[i]);
		}
		
	}
	
	// mytripinfo
	@Override
	public void addMytripInfo(MytripInfoDto mytripInfoDto) throws Exception {
		mytripMapper.addMytripInfo(mytripInfoDto);
	}

	@Override
	public MytripInfoDto getMytripInfo(MytripInfoDto mytripInfoDto) throws Exception {
		return mytripMapper.getMytripInfo(mytripInfoDto);
	}

	@Override
	public void deleteMytripInfo(MytripInfoDto mytripInfoDto) throws Exception {
		mytripMapper.deleteMytripInfo(mytripInfoDto);
	}

}
