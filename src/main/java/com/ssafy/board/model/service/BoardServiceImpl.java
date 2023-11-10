package com.ssafy.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
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
	public List<BoardDto> list(BoardParameterDto boardParameterDto) throws Exception {
		return boardMapper.list(boardParameterDto);
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

}
