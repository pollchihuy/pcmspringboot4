package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.dto.validasi.*;
import com.juaracoding.pcmspringboot4.handler.ResponseHandler;
import com.juaracoding.pcmspringboot4.model.User;
import com.juaracoding.pcmspringboot4.security.AESGeneratedKey;
import com.juaracoding.pcmspringboot4.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//        return new ResponseHandler().handleResponse("DATA DITERIMA", HttpStatus.OK,"OK",null,request);
    }

    @PostMapping("/regis/upload/{id}")
    public ResponseEntity<Object>  registrationUpload(
            @PathVariable Long id,
            @RequestParam MultipartFile file
            , HttpServletRequest request

    ){
        return authService.getImage(id,file,request);
//        return new ResponseHandler().handleResponse("BERHASIL UPLOAD", HttpStatus.OK,"OK",null,request);
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

    @PostMapping("/lupapasswordstepone")
    public Object lupaPasswordStepOne(@Valid @RequestBody LupaPasswordStepOneDTO lupaPasswordStepOneDTO,
    HttpServletRequest request){

        User user = new User();
        user.setEmail(lupaPasswordStepOneDTO.getEmail());
        return authService.lupaPasswordStepOne(user, request);
    }

    @PostMapping("/lupapasswordsteptwo")
    public Object lupaPasswordStepTwo(@Valid @RequestBody LupaPasswordStepTwoDTO lupaPasswordStepTwoDTO,
                                      HttpServletRequest request){

        User user = new User();
        user.setEmail(lupaPasswordStepTwoDTO.getEmail());
        user.setTokenEstafet(lupaPasswordStepTwoDTO.getTokenEstafet());
        user.setOtp(lupaPasswordStepTwoDTO.getOtp());
        return authService.lupaPasswordStepTwo(user, request);
    }

    @PostMapping("/lupapasswordstepthree")
    public Object lupaPasswordStepThree(@Valid @RequestBody LupaPasswordStepThreeDTO lupaPasswordStepThreeDTO,
                                      HttpServletRequest request){

        User user = new User();
        user.setEmail(lupaPasswordStepThreeDTO.getEmail());
        user.setPassword(lupaPasswordStepThreeDTO.getPassword());
        user.setPasswordConfirmation(lupaPasswordStepThreeDTO.getPasswordConfirmation());
        user.setTokenEstafet(lupaPasswordStepThreeDTO.getTokenEstafet());
        return authService.lupaPasswordStepThree(user, request);
    }


}
