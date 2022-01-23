package com.github.nagatosingle.controller;

import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.NagatoDeviceCategory;
import com.github.nagatosingle.entity.NagatoDeviceDetail;
import com.github.nagatosingle.entity.NagatoDeviceRFID;
import com.github.nagatosingle.entity.request.DeviceLendingFormDTO;
import com.github.nagatosingle.entity.request.DeviceLendingPicDTO;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.github.nagatosingle.service.interfaces.DeviceService;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import com.github.nagatosingle.utils.minio.MinioController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.github.nagatosingle.controller.BaseController.getToken;
import static com.github.nagatosingle.controller.BaseController.returnStatement;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@RestController
@RequestMapping("/platform/device")
@Slf4j
public class DeviceController {
    @Autowired
    public DeviceService deviceService;
    @Autowired
    public MinioController minioController;
    @Autowired
    public JwtTokenService tokenService;
    
    /**
     * 获取所有设备信息
     * @return ResponseEntity
     */
    @GetMapping("/list")
    public ResponseEntity<?> getDeviceList() {
        List<NagatoDeviceDetail> list = new ArrayList<>();
        List<String> picList = new ArrayList<>();
        picList.add("/nagato/{4WL$$4]IR0@YBYYZF{[9{6.png");
        picList.add("/nagato/{92]EKSEKRIKAHU@23UH]SM.png");
        picList.add("/nagato/P7SSIZV{MM@A0C8WXL`DHY5.png");
        list.add(NagatoDeviceDetail
                .builder()
                        .deviceUuid(UUID.randomUUID().toString())
                        .deviceName("fe87")
                        .deviceProductionDate(new Date(1577808000000L))
                        .deviceManufacturer("艾石头")
                        .deviceServiceLifeFromDate(new Date(1582992000000L))
                        .deviceServiceLifeToDate(new Date(1709222400000L))
                        .isKeyDevice(0)
                        .gmtCreate(new Date())
                        .devicePicList(picList)
                        .category(NagatoDeviceCategory
                                .builder()
                                .categoryName("键盘")
                                .categoryType(1)
                                .isKeyDeviceType(0)
                                .gmtCreate(new Date())
                                .build()
                        )
                        .nagatoDeviceInUse(null)
//                        .deviceRFID(NagatoDeviceRFID
//                                .builder()
//                                .rfidUuid(UUID.randomUUID().toString())
//                                .gmtCreate(new Date())
//                                .build()
//                        )
                .build()
        );
        picList.clear();
        picList.add("/nagato/~YO7F7UTQ3DK8(HO%})[KRW.png");
        picList.add("/nagato/ZD9UHMM1U~]7U4[AZ6W2~E1.png");
        picList.add("/nagato/GT@$OBXF9S(O`7L7]%OCT5A.png");
        list.add(NagatoDeviceDetail
                .builder()
                .deviceUuid(UUID.randomUUID().toString())
                .deviceName("VX2771-2K-HD")
                .deviceProductionDate(new Date(1577808222000L))
                .deviceManufacturer("ViewSonic")
                .deviceServiceLifeFromDate(new Date(1582933222000L))
                .deviceServiceLifeToDate(new Date(1709777666000L))
                .isKeyDevice(0)
                .gmtCreate(new Date())
                .devicePicList(picList)
                .category(NagatoDeviceCategory
                        .builder()
                        .categoryName("显示器")
                        .categoryType(1)
                        .isKeyDeviceType(0)
                        .gmtCreate(new Date())
                        .build()
                )
                .nagatoDeviceInUse(null)
//                .deviceRFID(NagatoDeviceRFID
//                        .builder()
//                        .rfidUuid(UUID.randomUUID().toString())
//                        .gmtCreate(new Date())
//                        .build()
//                )
                .build()
        );
        
        return returnStatement(new NagatoResponseEntity()
                .data(list)
                .message(ResponseMessage.OK)
        );
    }

    @PostMapping("/lending")
    public ResponseEntity<?> lending(@RequestBody DeviceLendingFormDTO form, HttpServletRequest request) {
        log.info(form.toString());
        String token = getToken(request);
        String userUuid = (String) tokenService.getClaimFromToken(token, claims -> claims.get("id"));
        // 从 token 中读取 uuid 写入
        form.setUserLendingUuid(userUuid);
        deviceService.createLendingEvent(form);
        return returnStatement(null);
    }

    @PostMapping("/lendingPic")
    public ResponseEntity<?> lendingPic(@RequestParam("picList") MultipartFile[] files,
                                        @RequestParam("deviceUuid") String deviceUuid
                                        ) {
        ResponseEntity<?> res = minioController.putObject(files);
        NagatoResponseEntity nagatoRes = (NagatoResponseEntity) res.getBody();
        List<String> imageList;
        if (nagatoRes != null && StringUtils.equals(nagatoRes.getMessage(), ResponseMessage.MINIO_SERVER_ERROR)) {
            return res;
        }
        imageList = (List<String>) nagatoRes.getData();

        deviceService.createLendingEventPic(imageList, deviceUuid);
        return returnStatement(null);
    }

    @GetMapping("/select")
    public ResponseEntity<?> select() {

        return returnStatement(null);
    }
    
    /**
     * 模糊查询 给出param
     * @return ResponseEntity
     */
    @GetMapping("/searchForDevice")
    public ResponseEntity<?> searchForDevice() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    /**
     * 分类查询
     * @return ResponseEntity
     */
    @GetMapping("/category")
    public ResponseEntity<?> category() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * 当前设备使用人员信息查询
     * @return ResponseEntity
     */
    @GetMapping("/info/user")
    public ResponseEntity<?> currentDeviceUsing() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}
