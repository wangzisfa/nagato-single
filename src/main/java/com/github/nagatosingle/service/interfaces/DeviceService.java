package com.github.nagatosingle.service.interfaces;

import com.github.nagatosingle.entity.request.DeviceLendingFormDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;

import java.util.List;

public interface DeviceService {
	NagatoResponseEntity createLendingEvent(DeviceLendingFormDTO form);
	NagatoResponseEntity createLendingEventPic(List<String> imageList, String deviceUuid);
}
