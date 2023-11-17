package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.user.model.UserDto;

public interface UserService {
	
	UserDto login(UserDto userDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;

/////////////////////////////JWT토큰 처리 전/////////////////////////////////
	int idCheck(String userId) throws Exception;
	void regist(UserDto userDto) throws Exception;
	void modify(UserDto userDto) throws Exception;
	void delete(String userId) throws Exception;
	void changePwd(UserDto userDto) throws Exception;
	String findPwd(String userId) throws Exception;
	
	/* Admin */
	List<UserDto> list() throws Exception;
	UserDto getUser(String userId) throws Exception;
	
}
