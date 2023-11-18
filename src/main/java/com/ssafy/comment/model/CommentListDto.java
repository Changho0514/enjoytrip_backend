package com.ssafy.comment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentListDto {

    List<CommentResultDto> comments;
    private int currentPage;
    private int totalPageCount;
}
