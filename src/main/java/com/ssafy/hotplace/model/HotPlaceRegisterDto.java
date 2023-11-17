package com.ssafy.hotplace.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class HotPlaceRegisterDto {
    private int hotplaceNo;
    private String userId;
    private String title;
    private String content;
    private String address;
    private String userName;
    private int recommendation;
    private List<FileInfoDto> fileInfos;
    private String date;
}
