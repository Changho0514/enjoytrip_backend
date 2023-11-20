package com.ssafy.board.model.service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.BoardParameterDto;

public interface BoardService {
	
	BoardDto getBoard(int articleNo) throws Exception;
	BoardListDto list(BoardParameterDto boardParameterDto) throws Exception;
	void write(BoardDto boardDto) throws Exception;
	void modify(BoardDto boardDto) throws Exception;
	void delete(int articleNo) throws Exception;

    void increaseHit(int articleNo) throws Exception;
	void increaseComment(int articleNo) throws Exception;
	void decreaseComment(int articleNo) throws Exception;



//	void increaseCommentCount(int articleNo) throws Exception;
}
