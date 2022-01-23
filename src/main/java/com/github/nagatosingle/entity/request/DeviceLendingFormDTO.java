package com.github.nagatosingle.entity.request;

import lombok.Data;

import java.util.Date;

/**
 * 设备借出表单 dto
 */
@Data
public class DeviceLendingFormDTO {
	// user uuid 忽略, 直接发token就行
	public String userLendingUuid;
	// 设备对应的 uuid
	public String deviceLendingUuid;
	// 设备借出时间
	public Date deviceLendingFromDate;
	// 设备预期归还时间
	public Date deviceLendingToDate;
	// 使用情况说明
	public String deviceLendingNotice;
	// 位置信息
	public String deviceUsePositionDetail;
}
