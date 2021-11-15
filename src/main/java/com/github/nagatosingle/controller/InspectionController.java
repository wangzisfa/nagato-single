package com.github.nagatosingle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/platform/inspection")
public class InspectionController {
    
    @GetMapping("/list")
    public ResponseEntity<?> getInspectionList() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * 结束巡检时间表单
     * @return ResponseEntity
     */
    @PostMapping("/resultForm")
    public ResponseEntity<?> resultForm() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * 开启巡检时间表单
     * @return ResponseEntity
     */
    @PostMapping("/startForm")
    public ResponseEntity<?> startForm() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * 设备保修表单
     * @return ResponseEntity
     */
    @PostMapping("/maintain")
    public ResponseEntity<?> maintain() {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
