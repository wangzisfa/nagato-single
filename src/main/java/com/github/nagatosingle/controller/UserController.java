package com.github.nagatosingle.controller;

import com.github.nagatosingle.service.interfaces.UserService;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import com.github.nagatosingle.utils.jwt.JwtUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/platform/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService tokenService;
    
    @GetMapping("/applyForInspector")
    public ResponseEntity<?> applyForInspector() {
        
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(token.indexOf(" ") + 1);
        String uuid = (String) tokenService.getClaimFromToken(token, claims -> claims.get("id"));
        log.info("userProfile token " + token);
        log.info("userProfile uuid " + uuid);
        return returnStatement(userService.findByUuid(uuid));
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
