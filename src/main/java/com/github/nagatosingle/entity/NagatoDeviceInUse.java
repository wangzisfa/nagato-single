package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 设备使用中/借出中查看设备信息
 */
@Builder
public class NagatoDeviceInUse {
	// 是否正在使用中
	public int isInUse;
	// 是否正在借出中
	public int isOnLending;
	// user_no
	public int userLendingNo;
	// user uuid
	public String userLendingUuid;
	// device_no
	public int deviceNo;
	// device uuid
	public String deviceUuid;
	// 设备借出时间
	public Date deviceLendingFromDate;
	// 设备预计归还时间
	public Date deviceLendingToDate;
	// device in use 事件创建时间
	public Date gmtCreate;

	// notice
	public NagatoDeviceInUseNotice nagatoDeviceInUseNotice;
	// notice pic
	public List<NagatoDeviceInUseNoticePic> nagatoDeviceInUseNoticePicList;
	// rfid标签
	public NagatoDeviceRFID deviceRFID;
	// 设备分类
	public NagatoDeviceCategory category;
}
