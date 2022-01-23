package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
public class NagatoDeviceCategory {
	public int categoryType;
	public String categoryName;
	public int isKeyDeviceType;
	public Date gmtCreate;
}
