package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 设备归还详细信息
 */
@Data
public class NagatoDeviceReturnDetail {
	// 设备归还说明
	public String deviceReturnNotice;
	// 设备归还位置信息
	public String deviceReturnPositionDetail;
	// 归还时间
	public Date deviceReturnDate;
	// 归还拍照
	public List<NagatoDevicePic> devicePicList;

	@Data
	static class NagatoDevicePic {
		public String devicePic;
		public Date gmtCreate;
	}
}
