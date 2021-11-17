package com.github.nagatosingle.configure;

import com.github.nagatosingle.exception.auth.CustomBadCredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 * <p>
 * date: 2021/11/08
 *
 * @author wangzisfa
 * @version 0.31
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NagatoCorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
//            bad credentials 异常捕获
            System.out.println("reqw" + req.getParameter("username"));
    
            try {
                chain.doFilter(req, resp);
        
            } catch (Exception exception) {
                System.out.println(exception.toString());
                System.out.println("nimasile");
                throw new CustomBadCredentialsException("用户名或密码错误");
            }
        }
    }
}
