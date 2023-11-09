package com.ssafy.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.model.UserDto;

@Mapper
public interface UserMapper {

	int idCheck(String userId) throws SQLException;
	void regist(UserDto userDto) throws SQLException;
	UserDto login(UserDto userDto) throws SQLException;
	
	/* Admin */
	List<UserDto> list() throws SQLException;
	UserDto getUser(String userId) throws SQLException;
	void modify(UserDto userDto) throws SQLException;
	void delete(String userId) throws SQLException;
	
}
