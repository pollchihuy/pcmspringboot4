package com.juaracoding.pcmspringboot4.handler;

import com.juaracoding.pcmspringboot4.config.OtherConfig;
import com.juaracoding.pcmspringboot4.util.LoggingFile;
import com.juaracoding.pcmspringboot4.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Code Error X
 *  VALIDATION = 01 //100
 *  DATABASE = 02 //100
 *  AUTH / PERMISSION = 03 //100
 *  MEDIA / FILE = 04 //100
 *  EXTERNAL API / 3rd Party = 05 //100
 *  OTHER       = 99 //100
 */
@RestControllerAdvice
@Configuration
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    List<Map<String,Object>> errors = new ArrayList<>();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        errors.clear();
        List<FieldError>  fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            Map<String,Object> mapError = new HashMap<>();
            mapError.put("field", fieldError.getField());
            mapError.put("message", fieldError.getDefaultMessage());
//            mapError.put("rejectedValue", fieldError.getRejectedValue());
            errors.add(mapError);
        }
        LoggingFile.logException("GlobalExceptionHandler","handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)  Request Package : "+ RequestCapture.allRequest(request),ex);
        return new ResponseHandler().handleResponse("REQUEST TIDAK VALID", HttpStatus.BAD_REQUEST,errors,"X01001",request);
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<Object> unexpectedRollbackException(UnexpectedRollbackException ex, HttpServletRequest request){
        LoggingFile.logException("GlobalExceptionHandler","unexpectedRollbackException(UnexpectedRollbackException ex, HttpServletRequest request) ",ex);
        return new ResponseHandler().handleResponse("Kesalahan Input Data , Contoh : Entry Data duplikat",HttpStatus.BAD_REQUEST,null,"X02001",request);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> authorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request){
        LoggingFile.logException("GlobalExceptionHandler","authorizationDeniedException(authorizationDeniedException ex, HttpServletRequest request) ",ex);
        return new ResponseHandler().handleResponse("AKSES DITOLAK !!",HttpStatus.FORBIDDEN,null,"X03001",request);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeException(RuntimeException ex, HttpServletRequest request){
        LoggingFile.logException("GlobalExceptionHandler","runtimeException(RuntimeException ex, HttpServletRequest request) ",ex);
        return new ResponseHandler().handleResponse("Terjadi Kesalahan Di Server",HttpStatus.INTERNAL_SERVER_ERROR,null,"X05001",request);
    }

    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        LoggingFile.logException("GlobalExceptionHandler","handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) ",ex);
        return new ResponseHandler().handleResponse("Terjadi Kesalahan Di Server",status,null,"X05999",request);
    }
}