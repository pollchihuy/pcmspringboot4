package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.dto.validasi.LoginDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.RegisDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.VerifyRegisDTO;
import com.juaracoding.pcmspringboot4.security.AESGeneratedKey;
import com.juaracoding.pcmspringboot4.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/regis")
    public ResponseEntity<Object> registration(@Valid @RequestBody RegisDTO regisDTO
            , HttpServletRequest request

    ){
        return authService.regis(authService.mapToUser(regisDTO),request);
    }

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegis(@Valid @RequestBody VerifyRegisDTO verifyRegisDTO
            , HttpServletRequest request){
        return authService.verifyRegis(authService.mapToUser(verifyRegisDTO),request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO
            , HttpServletRequest request){
        return authService.login(authService.mapToUser(loginDTO),request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> tokenExpired(@Valid @RequestBody LoginDTO loginDTO
            , HttpServletRequest request){
        return authService.refreshToken(authService.mapToUser(loginDTO),request);
    }

    @GetMapping("/gen-key")
    public String tokenExpired(){
        return "Your Key : "+ AESGeneratedKey.getKey();
    }
}
