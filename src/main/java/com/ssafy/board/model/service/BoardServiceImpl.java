package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	public BoardDto getBoard(int articleNo) throws Exception {
		return boardMapper.getBoard(articleNo);
	}

	@Override
	public BoardListDto list(BoardParameterDto boardParameterDto) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", boardParameterDto.getWord()== null ? "" : boardParameterDto.getWord());
		int currentPage = boardParameterDto.getPgno();
		int sizePerPage = boardParameterDto.getSpp();
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = boardParameterDto.getKey();
		param.put("key", key == null ? "" : key);
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "b.user_id");

		System.out.println("가~~~ㄴ니?"+ param);
		List<BoardDto> list = boardMapper.list(param);
		System.out.println("있~~~니?");

		int totalArticleCount = boardMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

	@Override
	public void write(BoardDto boardDto) throws Exception {
		boardMapper.write(boardDto);
	}

	@Override
	public void modify(BoardDto boardDto) throws Exception {
		boardMapper.modify(boardDto);
	}

	@Override
	public void delete(int articleNo) throws Exception {
		boardMapper.delete(articleNo);
	}
	
	@Override
	public void increaseHit(int articleNo) throws Exception {
		boardMapper.increaseHit(articleNo);
	}

	@Override
	public void increaseComment(int articleNo) throws Exception {
		boardMapper.increaseComment(articleNo);
	}

	@Override
	public void decreaseComment(int articleNo) throws Exception {
		boardMapper.decreaseComment(articleNo);
	}

	@Override
	public List<BoardDto> userlist(String userId) throws Exception {
		return boardMapper.userlist(userId);
	}

	@Override
	public String check(int articleNo) throws Exception {
		return boardMapper.check(articleNo);
	}

}
