package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
//	
//	public UserServiceImpl(UserMapper userMapper) {
//		super();
//		this.userMapper = userMapper;
//	}
	

	@Override
	public int idCheck(String userId) throws Exception {
		return userMapper.idCheck(userId);
	}

	@Override
	public void regist(UserDto userDto) throws Exception {
		userMapper.regist(userDto);
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		return userMapper.login(userDto);
	}
	
	/* ADMIN */
	@Override
	public List<UserDto> list() throws Exception {
		return userMapper.list();
	}

	@Override
	public UserDto getUser(String userId) throws Exception {
		return userMapper.getUser(userId);
	}

	@Override
	public void modify(UserDto userDto) throws Exception {
		userMapper.modify(userDto);
	}

	@Override
	public void delete(String userId) throws Exception {
		userMapper.delete(userId);	
	}

}
