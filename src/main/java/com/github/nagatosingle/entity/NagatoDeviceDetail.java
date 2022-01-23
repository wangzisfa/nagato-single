package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
public class NagatoDeviceDetail {
	// device_serial
	public String deviceUuid;
	// 设备名称
	public String deviceName;
	// 设备生产日期
	public Date deviceProductionDate;
	// 设备制造商
	public String deviceManufacturer;
	// 设备服役开始时间
	public Date deviceServiceLifeFromDate;
	// 设备预期服役结束时间
	public Date deviceServiceLifeToDate;
	// 星标设备
	public int isKeyDevice;
	// 设备添加时间
	public Date gmtCreate;
	// 设备的照片
	public List<String> devicePicList;

	// 设备的类型信息
	public NagatoDeviceCategory category;
	// 设备使用中/借出中 情况, 如果设备空闲则为null
	public NagatoDeviceInUse nagatoDeviceInUse;
	// 设备的RFID标签
//	public NagatoDeviceRFID deviceRFID;
}
