package com.ssafy.comment.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
	
    private int commentNo;
    private int articleNo;
    private String userId;
    private String content;
    private String date;
}
