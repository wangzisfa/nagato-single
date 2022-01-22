package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NagatoDeviceCategory {
	public int categoryType;
	public String categoryName;
	public int isKeyDeviceType;
	public Date gmtCreate;
}
