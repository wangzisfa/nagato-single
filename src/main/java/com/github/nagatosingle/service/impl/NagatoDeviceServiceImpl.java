package com.github.nagatosingle.service.impl;

import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.dao.DeviceMapper;
import com.github.nagatosingle.dao.UserMapper;
import com.github.nagatosingle.entity.NagatoDeviceInUse;
import com.github.nagatosingle.entity.NagatoDeviceInUseNotice;
import com.github.nagatosingle.entity.NagatoDeviceInUseNoticePic;
import com.github.nagatosingle.entity.request.DeviceLendingFormDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.service.interfaces.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NagatoDeviceServiceImpl implements DeviceService {
	@Autowired
	public DeviceMapper deviceMapper;
	@Autowired
	public UserMapper userMapper;
	/**
	 * 逻辑主要是创建 nagato_device_user 和 nagato_device_in_use_notice
	 * @param form 表单
	 * @return -
	 */
	@Override
	@Transactional
	public NagatoResponseEntity createLendingEvent(DeviceLendingFormDTO form) {
		int userNo = userMapper.findUserNoByUserNoGenerate(form.getUserLendingUuid());
		int deviceNo = deviceMapper.findDeviceNoByDeviceNoGenerate(form.getDeviceLendingUuid());
		NagatoDeviceInUse deviceInUse = NagatoDeviceInUse
				.builder()
				.isInUse(0)
				.isOnLending(1)
				.userLendingNo(userNo)
				.userLendingUuid(form.getUserLendingUuid())
				.deviceNo(deviceNo)
				.deviceUuid(form.getDeviceLendingUuid())
				.deviceLendingFromDate(form.getDeviceLendingFromDate())
				.deviceLendingToDate(form.getDeviceLendingToDate())
				.nagatoDeviceInUseNotice(NagatoDeviceInUseNotice
						.builder()
						.notice(form.getDeviceLendingNotice())
						.deviceNo(deviceNo)
						.deviceUsePositionDetail(form.getDeviceUsePositionDetail())
						.build()
				)
				.build();
		deviceMapper.createNagatoDeviceUser(deviceInUse);
		deviceMapper.createNagatoDeviceInUseNotice(deviceInUse.nagatoDeviceInUseNotice);

		return new NagatoResponseEntity()
				.message(ResponseMessage.OK);
	}

	/**
	 * 这边实际上是创建 nagato_device_in_use_notice_pic_url
	 * @param imageList 图片
	 * @return -
	 */
	@Override
	public NagatoResponseEntity createLendingEventPic(List<String> imageList, String deviceUuid) {
		int deviceNo = deviceMapper.findDeviceNoByDeviceNoGenerate(deviceUuid);
		int deviceInUseNoticeNo = deviceMapper.findDeviceInUseNoticeNoByDeviceNo(deviceNo);
		List<NagatoDeviceInUseNoticePic> list = new ArrayList<>();
		for (String image : imageList) {
			NagatoDeviceInUseNoticePic pic = NagatoDeviceInUseNoticePic
					.builder()
					.deviceInUseNoticeNo(deviceInUseNoticeNo)
					.picURI(image)
					.build();
			list.add(pic);
		}
//		NagatoDeviceInUseNotice notice = NagatoDeviceInUseNotice
//				.builder()
//				.deviceInUseNoticeNo(deviceInUseNoticeNo)
//				.picDetailList(list)
//				.build();
		deviceMapper.createNagatoDeviceInUseNoticePic(list);
		return null;
	}
}
