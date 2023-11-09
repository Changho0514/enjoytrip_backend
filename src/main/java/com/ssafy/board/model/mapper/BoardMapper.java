package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.BoardDto;

public interface BoardMapper {
	
	BoardDto getBoard(int articleNo) throws SQLException;
	List<BoardDto> list() throws SQLException;
	void write(BoardDto boardDto) throws SQLException;
	void modify(BoardDto boardDto) throws SQLException;
	void delete(int articleNo) throws SQLException;

}
