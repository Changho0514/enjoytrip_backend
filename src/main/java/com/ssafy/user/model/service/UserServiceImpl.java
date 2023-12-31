package com.ssafy.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.mapper.UserMapper;
import com.ssafy.user.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}
	
	@Override
	public UserDto login(UserDto userDto) throws Exception {
		String salt = PasswordUtil.generateSalt();
		userDto.setSalt(salt);
		userDto.setUserPwd(PasswordUtil.generateHash(userDto.getUserPwd(), salt));
		return userMapper.login(userDto);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}
	
/////////////////////////////JWT 토큰/////////////////////////////////
	@Override
	public int idCheck(String userId) throws Exception {
		return userMapper.idCheck(userId);
	}

	@Override
	public void regist(UserDto userDto) throws Exception {
		String salt = PasswordUtil.generateSalt();
		userDto.setSalt(salt);
		userDto.setUserPwd(PasswordUtil.generateHash(userDto.getUserPwd(), salt));
		userMapper.regist(userDto);
//		if(userMapper.getOutUser(userDto.getUserId()) != null) { // 탈퇴했던 회원이라면
//			userMapper.updateRegist(userDto);
//		} else {
//			userMapper.regist(userDto);
//		}
	}
	
	@Override
	public void modify(UserDto userDto) throws Exception {
		userMapper.modify(userDto);
	}
	
	@Override
	public void delete(String userId) throws Exception {
		userMapper.delete(userId);	
	}
	
	@Override
	public void changePwd(UserDto userDto) throws Exception {
		String salt = PasswordUtil.generateSalt();
		userDto.setSalt(salt);
		userDto.setUserPwd(PasswordUtil.generateHash(userDto.getUserPwd(), salt));
		userMapper.changePwd(userDto);
	}
	
	@Override
	public String findPwd(String userId) throws Exception {
		return userMapper.findPwd(userId);
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
	public int isAdmin(String userId) throws Exception {
		return userMapper.isAdmin(userId);
	}

	// 프로필 업로드
	@Override
	public void writeFile(Map<String, Object> params) throws Exception {
		userMapper.writeFile(params);
	}

}
