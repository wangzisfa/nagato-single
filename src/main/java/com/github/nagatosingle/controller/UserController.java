package com.github.nagatosingle.controller;

import com.github.nagatosingle.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * <p>
 * date: 2021/11/07
 *
 * @author wangzisfa
 * @version 0.31
 */
@RestController
@RequestMapping("/platform/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/applyForInspector")
    public ResponseEntity<?> applyForInspector() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(@RequestBody String username) {
        
        
        return new ResponseEntity<>(userService.findByUsername(username).getData(), HttpStatus.OK);
    }
    
    /**
     * 用户主界面请求信息
     * @return ResponseEntity
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> userDashboard(@RequestParam("uuid") String uuid) {
        System.out.println(userService.getUserDetailInfoByUserGenerated(uuid));
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
