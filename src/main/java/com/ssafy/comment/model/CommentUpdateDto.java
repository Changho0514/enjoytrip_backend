package com.ssafy.comment.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {
    private String content;
    private int commentNo;

}
