package com.ssafy.mytrip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.mytrip.model.MytripDto;


public interface MytripMapper {
	
	List<MytripDto> getMytrip(MytripDto mytripDto) throws SQLException;
	void addMytrip(MytripDto mytripDto) throws SQLException;
	int getMytripMax(String userId) throws SQLException;
	List<Integer> getMytripAll(String userId) throws SQLException;
    void deleteMytripAll(MytripDto mytripDto) throws SQLException;
	void deleteMytrip(int no) throws SQLException;
	int getMytripCount(String userId) throws SQLException;

}
