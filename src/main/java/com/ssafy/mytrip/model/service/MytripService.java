package com.ssafy.mytrip.model.service;

import java.util.List;

import com.ssafy.mytrip.model.MytripDto;

public interface MytripService {
	List<MytripDto> getMytrip(MytripDto mytripDto) throws Exception;
	void addMytrip(MytripDto mytripDto) throws Exception;
	int getMytripMax(String userId) throws Exception;
	List<Integer> getMytripAll(String userId) throws Exception;
    void deleteMytripAll(MytripDto mytripDto) throws Exception;
	void deleteMytrip(int no) throws Exception;
	int getMytripCount(String userId) throws Exception;
	void addMytripAll(MytripDto[] list) throws Exception;
}
