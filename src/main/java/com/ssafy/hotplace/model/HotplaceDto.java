package com.ssafy.hotplace.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceDto {
    private int hotplaceNo;
    private String userId;
    private String title;
    private String content;
    private String address;
    private String userName;
    private int recommendation;
    private String saveFolder;
    private String originalFile;
    private String saveFile;
	private String date;
    private int aid;
}
