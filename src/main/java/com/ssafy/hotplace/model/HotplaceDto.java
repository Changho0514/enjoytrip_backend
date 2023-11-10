package com.ssafy.hotplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HotplaceDto {
	
	private int hotplaceNo;
	private String userId;
	private String title;
	private String content;
	private String address;
	private int recommendation;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	private String date;

}
