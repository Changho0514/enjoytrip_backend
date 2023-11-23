package com.ssafy.comment.model.service;

import com.ssafy.comment.model.CommentDto;

import java.util.List;

public interface CommentService {
    void write(CommentDto commentDto) throws Exception;
    List<CommentDto> list(int articleNo) throws Exception;
    void modify(CommentDto commentDto) throws Exception;
    void delete(int commentNo) throws Exception;
    String check(int commentNo) throws Exception;

    int getArticleNo(int commentNo) throws Exception;
    
    List<CommentDto> userlist(String userId) throws Exception;
}
