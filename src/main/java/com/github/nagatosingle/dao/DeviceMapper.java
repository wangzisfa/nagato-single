package com.github.nagatosingle.dao;

import com.github.nagatosingle.entity.NagatoDeviceDetail;
import com.github.nagatosingle.entity.NagatoDeviceInUse;
import com.github.nagatosingle.entity.NagatoDeviceInUseNotice;
import com.github.nagatosingle.entity.NagatoDeviceInUseNoticePic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {
	void createNagatoDeviceUser(NagatoDeviceInUse lending);
	void createNagatoDeviceInUseNotice(NagatoDeviceInUseNotice notice);
	void createNagatoDeviceInUseNoticePic(List<NagatoDeviceInUseNoticePic> list);

	NagatoDeviceDetail selectPage(int from, int to);

	int findDeviceInUseNoticeNoByDeviceNo(int deviceNo);
	int findDeviceNoByDeviceNoGenerate(String deviceUuid);
}
