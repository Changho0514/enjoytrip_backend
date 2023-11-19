package com.ssafy.comment.model.service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentListDto;
import com.ssafy.comment.model.CommentParameterDto;
import com.ssafy.comment.model.CommentUpdateDto;

import java.sql.SQLException;

public interface CommentService {
    void write(CommentDto commentDto) throws Exception;
    CommentListDto list(CommentParameterDto commentParameterDto) throws Exception;
    void modify(CommentUpdateDto commentUpdateDto) throws Exception;
    void delete(int commentNo) throws Exception;

    int getArticleNo(int commentNo) throws Exception;
}
