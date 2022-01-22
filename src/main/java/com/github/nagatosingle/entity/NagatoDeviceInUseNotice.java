package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NagatoDeviceInUseNotice {
	// 使用情况说明
	public String notice;
	public Date gmtCreate;
	public String deviceUsePositionDetail;
	public List<DeviceInUseNoticePic> picDetailList;

	@Data
	static class DeviceInUseNoticePic {
		public String picURI;
		public Date gmtCreate;
	}
}
