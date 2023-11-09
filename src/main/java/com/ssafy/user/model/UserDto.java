package com.ssafy.user.model;

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
public class UserDto {
	
	@NonNull
	private String userId;
	@NonNull
	private String userPwd;
	private String userName;
	@NonNull
	private String emailId;
	@NonNull
	private String emailDomain;
	private int role;
	private int flag;
	private String joinDate;
}