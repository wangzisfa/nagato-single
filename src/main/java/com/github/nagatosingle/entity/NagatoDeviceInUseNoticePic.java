package com.github.nagatosingle.entity;

import lombok.Builder;

import java.util.Date;


@Builder
public class NagatoDeviceInUseNoticePic {
	public int deviceInUseNoticeNo;
	public String picURI;
	public Date gmtCreate;
}