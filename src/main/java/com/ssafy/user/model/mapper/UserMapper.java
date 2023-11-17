package com.ssafy.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.model.UserDto;

@Mapper
public interface UserMapper {
	
	UserDto login(UserDto userDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
/////////////////////////////JWT 토큰/////////////////////////////////
	int idCheck(String userId) throws SQLException;
	void regist(UserDto userDto) throws SQLException;
	UserDto getOutUser(String userId) throws SQLException;
	void updateRegist(UserDto userDto) throws SQLException;
	void modify(UserDto userDto) throws SQLException;
	void delete(String userId) throws SQLException;
	void changePwd(UserDto userDto) throws SQLException;
	String findPwd(String userId) throws SQLException;
	
	
	/* Admin */
	List<UserDto> list() throws SQLException;
	UserDto getUser(String userId) throws SQLException;
	
}
