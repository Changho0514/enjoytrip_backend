package com.ssafy.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class BoardDto {
	
	@NonNull
	private int articleNo;
	@NonNull
	private String userId;
	@NonNull
	private String subject;
	@NonNull
	private String content;
	private int hit;
	private int recommendation;
	private int comment;
	private String date;
	private int isnotice;

}