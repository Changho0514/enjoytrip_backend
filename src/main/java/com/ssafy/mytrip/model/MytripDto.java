package com.ssafy.mytrip.model;

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
public class MytripDto {
	
	private int userMytripNo;
	private int no;
	private int contentId;
	private String userId;

}
