package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NagatoDeviceInUse {
	public int isInUse;
	public int isOnLending;
	// user_no
	public int userLendingNo;
	public String userLendingUuid;
	public Date deviceLendingFromDate;
	public Date deviceLendingToDate;
	public Date gmtCreate;

	public NagatoDeviceInUseNotice nagatoDeviceInUseNotice;
	public NagatoDeviceRFID deviceRFID;
	public NagatoDeviceCategory category;
}
