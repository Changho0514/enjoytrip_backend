package com.ssafy.hotplace.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceParameterDto {
    private int pgno;
    private int spp;
    private String key;
    private String word;
    private int start;
    private int listsize;

    private int totalArticleCount;
    private int totalPageCount;
}