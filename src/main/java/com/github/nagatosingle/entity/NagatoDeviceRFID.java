package com.github.nagatosingle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NagatoDeviceRFID {
	public int rfidNo;
	public String rfidUuid;
	public Date gmtCreate;
}
