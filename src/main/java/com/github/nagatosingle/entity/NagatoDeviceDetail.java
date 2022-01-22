package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NagatoDeviceDetail {
	// device_serial
	public String deviceUuid;
	public String deviceName;
	public Date deviceProductionDate;
	public String deviceManufacturer;
	public Date deviceServiceLifeFromDate;
	public Date deviceServiceLifeToDate;
	public int isKeyDevice;
	public Date gmtCreate;
	// 设备的照片
	public List<String> devicePicList;

	// 设备的类型信息
	public NagatoDeviceCategory category;
	// 设备使用中/借出中 情况, 如果设备空闲则为null
	public NagatoDeviceInUse nagatoDeviceInUse;
	// 设备的RFID标签
	public NagatoDeviceRFID deviceRFID;
}
