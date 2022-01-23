package com.github.nagatosingle.entity.request;

import lombok.Data;

import java.util.Date;


/**
 * 设备归还表单 dto
 */
@Data
public class DeviceReturningFormDTO {
	// 设备归还说明
	public String deviceReturnNotice;
	// 设备归还位置信息
	public String deviceReturnPositionDetail;
	// 设备归还日期
	public Date deviceReturnDate;
}
