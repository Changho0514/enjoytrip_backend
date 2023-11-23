package com.ssafy.mytrip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.mytrip.model.MytripDto;
import com.ssafy.mytrip.model.MytripInfoDto;


public interface MytripMapper {
	
	List<MytripDto> getMytrip(MytripDto mytripDto) throws SQLException;
	void addMytrip(MytripDto mytripDto) throws SQLException;
	int getMytripMax(String userId) throws SQLException;
	List<Integer> getMytripAll(String userId) throws SQLException;
    void deleteMytripAll(MytripDto mytripDto) throws SQLException;
	void deleteMytrip(int no) throws SQLException;
	int getMytripCount(String userId) throws SQLException;
	// mytripInfo
	void addMytripInfo(MytripInfoDto mytripInfoDto) throws SQLException;
	MytripInfoDto getMytripInfo(MytripInfoDto mytripInfoDto) throws SQLException;
	void deleteMytripInfo(MytripInfoDto mytripInfoDto) throws SQLException;
}
