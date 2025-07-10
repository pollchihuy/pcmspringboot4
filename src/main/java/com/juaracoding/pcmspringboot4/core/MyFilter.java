//package com.juaracoding.pcmspringboot4.core;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class MyFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        if(servletRequest instanceof HttpServletRequest) {
//            Object obj = request.getContentType();
//            String strContentType = obj == null ? "" : obj.toString();
//            if(!strContentType.startsWith("multipart/form-data")) {
//                request = new MyHttpServletRequestWrapper(request);
//            }
//        }
//        filterChain.doFilter(request, servletResponse);
//    }
//}