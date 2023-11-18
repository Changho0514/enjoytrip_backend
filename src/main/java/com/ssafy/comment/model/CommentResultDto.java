package com.ssafy.comment.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentResultDto {
    private int commentNo;
    private int articleNo;
    private String userId;
    private String content;
    private String date;
}
