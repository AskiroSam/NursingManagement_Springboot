package com.stedu.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stedu.Util.JwtUtils;
import com.stedu.bean.RespBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 预判浏览器发送过来的JWT是否正确
         * 1.正确 - 放行
         * 2.错误 - 不放行，返回错误信息
         * */
        //对OPTIONS请求放行，不然会出现跨域问题
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        //获取token
        String token = request.getHeader("token");
        RespBean respBean = null;
        try {
            //解析JWT，如果出现问题会抛出异常
            JwtUtils.parseJwtToMap(token);
            return true;
        } catch (SignatureVerificationException e) {
            respBean = RespBean.error("无效签名");
        } catch (TokenExpiredException e) {
            respBean = RespBean.error("令牌超时");
        } catch (AlgorithmMismatchException e) {
            respBean = RespBean.error("算法不匹配");
        } catch (Exception e) {
            respBean = RespBean.error("令牌无效");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), respBean);
        return false;
    }
}
