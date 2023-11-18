package com.ssafy.comment.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentParameterDto {

    private int articleNo;
    private int pgno;
    private int spp;
}
