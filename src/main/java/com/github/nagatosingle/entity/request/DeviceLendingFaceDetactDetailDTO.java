package com.github.nagatosingle.entity.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class DeviceLendingFaceDetactDetailDTO {
	public MultipartFile facePic;
}
