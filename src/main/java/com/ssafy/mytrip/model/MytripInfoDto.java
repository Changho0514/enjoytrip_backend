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
public class MytripInfoDto {
	
	private int userMytripNo;
	private String userId;
	private String startDay;
	private String endDay;
	private String content;

}
