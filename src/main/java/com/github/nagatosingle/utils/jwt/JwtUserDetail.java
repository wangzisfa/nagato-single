package com.github.nagatosingle.utils.jwt;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class JwtUserDetail {
	private String username;
	private String userNoGenerate;
	private Date exp;
}
