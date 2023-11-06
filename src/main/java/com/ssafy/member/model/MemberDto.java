package com.ssafy.member.model;

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
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberDto {
	
	@NonNull
	private String userId;
	@NonNull
	private String userName;
	@NonNull
	private String userPwd;
	@NonNull
	private String emailId;
	@NonNull
	private String emailDomain;
	private int role;
	private String joinDate;
}
