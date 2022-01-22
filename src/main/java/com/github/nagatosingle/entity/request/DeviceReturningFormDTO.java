package com.github.nagatosingle.entity.request;

import lombok.Data;

import java.util.Date;


@Data
public class DeviceReturningFormDTO {
	public String deviceReturnNotice;
	public String deviceReturnPositionDetail;
	public Date deviceReturnDate;
}
