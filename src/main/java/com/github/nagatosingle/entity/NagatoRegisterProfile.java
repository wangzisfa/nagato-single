package com.github.nagatosingle.entity;


import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class NagatoRegisterProfile {
	public Integer userNo;
	public String uuid;
	public String realName;
	public String gender;
	public String email;
	public String userIconURI;
	public Long userFaceId;
	public String password;
//	public String passwordConfirm;
	public String username;
	public boolean isInspector;
	public int accessPropertyDevice;
	public int accessPropertyLog;
	public int accessPropertyUser;
}
