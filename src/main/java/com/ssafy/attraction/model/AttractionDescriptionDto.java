package com.ssafy.attraction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDescriptionDto {
	
	private int contentId;
	private String homepage;
	private String overview;
	private String telname;
}
