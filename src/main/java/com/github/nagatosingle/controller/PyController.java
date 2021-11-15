package com.github.nagatosingle.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * <p>
 * date: 2021/11/09
 *
 * @author wangzisfa
 * @version 0.31
 */
@RestController
@RequestMapping("/py")
public class PyController {
    
    @PostMapping("/readHook")
    public ResponseEntity<?> readHook(@RequestBody JSONObject obj) {
        System.out.println(obj);
        
        
        return new ResponseEntity<>(
                new NagatoResponseEntity()
                        .message("success"),
                HttpStatus.OK
        );
    }
    
    @GetMapping("/writeHook")
    public ResponseEntity<?> writeHook() {
        return ResponseEntity.status(200).body("success");
    }
}
