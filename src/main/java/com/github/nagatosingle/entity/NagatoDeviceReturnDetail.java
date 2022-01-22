package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NagatoDeviceReturnDetail {
	public String deviceReturnNotice;
	public String deviceReturnPositionDetail;
	public Date deviceReturnDate;
	public List<NagatoDevicePic> devicePicList;

	@Data
	static class NagatoDevicePic {
		public String devicePic;
		public Date gmtCreate;
	}
}
