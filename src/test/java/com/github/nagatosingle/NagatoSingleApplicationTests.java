package com.github.nagatosingle;

import com.alibaba.fastjson.JSONObject;
import com.github.nagatosingle.constants.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NagatoSingleApplicationTests {
    
    @Test
    void contextLoads() {
        JSONObject obj = new JSONObject();
        obj.put("message", "没有权限访问当前api");
        System.out.println(obj);
    }
}
