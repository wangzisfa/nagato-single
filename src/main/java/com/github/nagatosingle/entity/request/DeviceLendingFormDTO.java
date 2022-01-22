package com.github.nagatosingle.entity.request;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceLendingFormDTO {
	public String userLendingUuid;
	public Date deviceLendingFromDate;
	public Date deviceLendingToDate;
	// 使用情况说明
	public String deviceLendingNotice;
	public String deviceUsePositionDetail;
}
