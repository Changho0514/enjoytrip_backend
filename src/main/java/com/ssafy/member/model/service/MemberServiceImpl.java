package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private SqlSession sqlSession;

	public MemberServiceImpl(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		return sqlSession.getMapper(MemberMapper.class).idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		sqlSession.getMapper(MemberMapper.class).joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(Map<String, String> map) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		return sqlSession.getMapper(MemberMapper.class).loginMember(map);
	}
	
	/* ADMIN */
	@Override
	public List<MemberDto> listMember(Map<String, Object> map) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		return memberMapper.listMember(map);
	}

	@Override
	public MemberDto getMember(String userId) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		return memberMapper.getMember(userId);
	}

	@Override
	public void updateMember(MemberDto memberDto) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		memberMapper.updateMember(memberDto);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		memberMapper.deleteMember(userId);		
	}

}
