package com.juaracoding.pcmspringboot4.util;

import com.juaracoding.pcmspringboot4.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponse {

    public static ResponseEntity<Object> dataBerhasilDisimpan(HttpServletRequest request){
        return new ResponseHandler().handleResponse("DATA BERHASIL DISIMPAN", HttpStatus.CREATED,null,null,request);
    }

    public static ResponseEntity<Object> dataBerhasilDiubah(HttpServletRequest request){
        return new ResponseHandler().handleResponse("DATA BERHASIL DIUBAH", HttpStatus.OK,null,null,request);
    }

    public static ResponseEntity<Object> internalServerError(String errorCode, HttpServletRequest request){
        return new ResponseHandler().handleResponse("SERVER SEDANG MENGALAMI GANGGUAN", HttpStatus.INTERNAL_SERVER_ERROR,null,errorCode,request);
    }

    public static ResponseEntity<Object> objectNull(String errorCode, HttpServletRequest request){
        return new ResponseHandler().handleResponse("DATA TIDAK VALID", HttpStatus.BAD_REQUEST,null,errorCode,request);
    }
}
