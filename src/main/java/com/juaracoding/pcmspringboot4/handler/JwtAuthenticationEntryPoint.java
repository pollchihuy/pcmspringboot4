package com.juaracoding.pcmspringboot4.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/** Response Body untuk masalah JWT / Security */
@Component("customAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper ;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        /** Response Header */
        response.setHeader("Content-Type","application/json");
        int status = response.getStatus();
        status = status == 200 ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_FORBIDDEN;
        /** Response Code */
        String message = status==401?"Otentikasi Bermasalah":"Otorisasi Bermasalah";
        response.setStatus(status);
        /** Response Body */
        Map<String,Object> data = new HashMap<>();
        data.put("status",status);
        data.put("success",false);
        data.put("error_code","X01001");
        data.put("timestamp", Calendar.getInstance().getTime());
//        data.put("error",authException.getMessage());
        data.put("error",message);
        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}