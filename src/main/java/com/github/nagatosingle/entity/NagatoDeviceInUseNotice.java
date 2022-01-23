package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 设备使用/借出中状态说明
 */
@Builder
public class NagatoDeviceInUseNotice {
	// 使用情况说明
	public String notice;
	// 事件创建时间
	public Date gmtCreate;
	// 使用位置详细信息
	public String deviceUsePositionDetail;
	// 借出时拍照
	public List<NagatoDeviceInUseNoticePic> picDetailList;
	// device_in_use_notice_id
	public int deviceInUseNoticeNo;
	// device_no
	public int deviceNo;
}
