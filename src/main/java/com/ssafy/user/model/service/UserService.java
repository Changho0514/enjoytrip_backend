package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.user.model.UserDto;

public interface UserService {

	int idCheck(String userId) throws Exception;
	void regist(UserDto userDto) throws Exception;
	UserDto login(UserDto userDto) throws Exception;
	
	/* Admin */
	List<UserDto> list() throws Exception;
	UserDto getUser(String userId) throws Exception;
	void modify(UserDto userDto) throws Exception;
	void delete(String userId) throws Exception;
	
}
