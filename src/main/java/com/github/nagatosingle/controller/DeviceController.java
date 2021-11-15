package com.github.nagatosingle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class DeviceController {
    
    /**
     * 获取所有设备信息
     * @return ResponseEntity
     */
    @GetMapping("/list")
    public ResponseEntity<?> getDeviceList() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
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
