package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;

public interface BoardMapper {
	
	BoardDto getBoard(int articleNo) throws SQLException;
	List<BoardDto> list(BoardParameterDto boardParameterDto) throws SQLException;
	void write(BoardDto boardDto) throws SQLException;
	void modify(BoardDto boardDto) throws SQLException;
	void delete(int articleNo) throws SQLException;

}
