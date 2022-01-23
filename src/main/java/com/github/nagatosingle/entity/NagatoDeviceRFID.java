package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
public class NagatoDeviceRFID {
	public String rfidUuid;
	public Date gmtCreate;
}
