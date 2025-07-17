package com.juaracoding.pcmspringboot4.service;


import com.juaracoding.pcmspringboot4.config.JwtConfig;
import com.juaracoding.pcmspringboot4.config.OtherConfig;
import com.juaracoding.pcmspringboot4.dto.validasi.LoginDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.MenuLoginDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.RegisDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.VerifyRegisDTO;
import com.juaracoding.pcmspringboot4.handler.ResponseHandler;
import com.juaracoding.pcmspringboot4.model.Akses;
import com.juaracoding.pcmspringboot4.model.Menu;
import com.juaracoding.pcmspringboot4.model.User;
import com.juaracoding.pcmspringboot4.repo.UserRepo;
import com.juaracoding.pcmspringboot4.security.BcryptImpl;
import com.juaracoding.pcmspringboot4.security.Crypto;
import com.juaracoding.pcmspringboot4.security.JwtUtility;
import com.juaracoding.pcmspringboot4.util.LoggingFile;
import com.juaracoding.pcmspringboot4.util.RequestCapture;
import com.juaracoding.pcmspringboot4.util.SendMailOTP;
import com.juaracoding.pcmspringboot4.util.TransformationDataMenu;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Kode Platform / Aplikasi : 001 atau AUT
 * Kode Modul : 00
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;

    private Random random = new Random();

    /** 001-010 */
    public ResponseEntity<Object> regis(User user, HttpServletRequest request) {
        if(user==null){
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"TRN00FV001",request);
        }
        if(user.getEmail()==null){
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"TRN00FV002",request);
        }
        Map<String,Object> mapResponse = new HashMap<>();
        try{
            int otp = random.nextInt(100000,999999);//6 digit angkat 803822
            user.setOtp(BcryptImpl.hash(String.valueOf(otp)));
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            /** Default Akses untuk New member */
            Akses akses = new Akses();
            akses.setId(2L);
            user.setAkses(akses);

            userRepo.save(user);
            if(OtherConfig.getEnableAutomationTesting().equals("y")){
                mapResponse.put("otp",otp);// ini untuk automation
            }
            SendMailOTP.verifyRegisOTP("OTP UNTUK REGISTRASI",
                    user.getNamaLengkap(),user.getEmail(),String.valueOf(otp),"ver_regis.html");
            mapResponse.put("email",user.getEmail());
            Thread.sleep(1000);
        }catch (Exception e){
            LoggingFile.logException("AuthService","regis(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Server Tidak Dapat Memproses !!",HttpStatus.INTERNAL_SERVER_ERROR,null,"TRN00FE001",request);
        }
        return new ResponseHandler().handleResponse("OTP Terkirim, Cek Email !!",HttpStatus.OK,mapResponse,null,request);
    }

    /** 011-020 */
    public ResponseEntity<Object> verifyRegis(User user, HttpServletRequest request) {
        try {
            int otp = random.nextInt(100000,999999);
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"TRN00FV011",request);
            }
            User userNext = opUser.get();//ini dari database
            if(!BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp())) {
                return new ResponseHandler().handleResponse("OTP Salah !!",HttpStatus.BAD_REQUEST,null,"TRN00FV012",request);
            }
            userNext.setRegistered(true);
            userNext.setModifiedBy(userNext.getId());
            userNext.setOtp(BcryptImpl.hash(String.valueOf(otp)));
        }catch (Exception e){
            LoggingFile.logException("AuthService","verifyRegis(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE011",request);
        }
        return new ResponseHandler().handleResponse("Registrasi Berhasil !!",HttpStatus.OK,null,null,request);
    }

    /** 021-030 */
    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Map<String,Object> m = new HashMap<>();
        User userNext = null;
        try{
            String username = user.getUsername();
            Optional<User> opUser = userRepo.findByUsernameOrEmailOrNoHpAndIsRegistered(username,username,username,true);
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan",HttpStatus.BAD_REQUEST,null,"TRN00FV021",request);
            }
            userNext = opUser.get();//diambil dari DB

            String pwdDB = userNext.getUsername()+user.getPassword();
            if(!BcryptImpl.verifyHash(pwdDB,userNext.getPassword())) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!",HttpStatus.BAD_REQUEST,null,"TRN00FV022",request);
            }
        }catch (Exception e){
            LoggingFile.logException("AuthService","login(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE021",request);
        }
        /** SET PAYLOAD JWT */
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("em",userNext.getEmail());
        mapData.put("id",userNext.getId());
        mapData.put("hp",userNext.getNoHp());
        mapData.put("naleng",userNext.getNamaLengkap());
        List<MenuLoginDTO> menu = mapToMenuLoginDTO(userNext.getAkses().getListMenu());
        String token = jwtUtility.doGenerateToken(mapData,userNext.getUsername());

        m.put("menu",new TransformationDataMenu().doTransformAksesMenuLogin(menu));
//        m.put("menu",menu);
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token", token);
        m.put("urlImage", userNext.getLinkImage());
        return new ResponseHandler().handleResponse("Login Berhasil !!",HttpStatus.OK,m,null,request);
    }

    
    /** proses input email untuk verifikasi lupa password 031-040*/
    public ResponseEntity<Object> lupaPasswordStepOne(User user, HttpServletRequest request){
        int intOtp = 0;
        String strTokenEstafet = "";
        Map<String,Object> mapResponse = new HashMap<>();
        try {
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if(opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User Belum Terdaftar",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV031",request);
            }
            intOtp = random.nextInt(100000,999999);
            strTokenEstafet = BcryptImpl.hash(String.valueOf(intOtp));

            User userNext = opUser.get();
            userNext.setTokenEstafet(strTokenEstafet);
            userNext.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));

            /** untuk automation testing saja
             * Notes : jangan mengembalikan nilai yang sudah di hash !!
             * */
            if(OtherConfig.getEnableAutomationTesting().equals("y")){
                mapResponse.put("otp",intOtp);
            }
            mapResponse.put("estafet",strTokenEstafet);
        }catch (Exception e){
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE031",request);
        }

        return new ResponseHandler().handleResponse("OTP Berhasil Terkirim Ke Email",HttpStatus.OK,mapResponse,null,request);
    }

    /** proses verifikasi OTP yang sudah dikirim ke email di proses pertama password 041-050*/
    public ResponseEntity<Object> lupaPasswordStepTwo(User user, HttpServletRequest request){
        int intOtp = 0;
        String strTokenEstafet = "";
        Map<String,Object> mapResponse = new HashMap<>();
        try {
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if(opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User Belum Terdaftar",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV041",request);
            }
            User userNext = opUser.get();
            /** menggunakan equals karena hash nya yang dikirim ke client
             * menghemat sumber daya (penggunaan memory)
             */
            Boolean isValid = user.getTokenEstafet().equals(userNext.getTokenEstafet());
            if(!isValid) {
                return new ResponseHandler().handleResponse("Request Tidak Valid",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV042",request);
            }

            /** Verifikasi OTP yang dikirim ke Email
             *  dicocokkan dengan OTP yang disimpan sebelumnya ke database
             */
            isValid = BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp());
            if(!isValid) {
                return new ResponseHandler().handleResponse("OTP Salah !!",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV043",request);
            }
            intOtp = random.nextInt(100000,999999);
            strTokenEstafet = BcryptImpl.hash(String.valueOf(random.nextInt(99)));

            /** OTP dan Token Estafet tetap di update kedua nya untuk keamanan
                hanya saja informasi yang dikembalikan ke client di langkah yang ini hanya token estafet saja
                karena langkah selanjutnya , user hanya memasukkan password baru saja
            */
            userNext.setTokenEstafet(strTokenEstafet);
            userNext.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
            mapResponse.put("estafet",strTokenEstafet);
        }catch (Exception e){
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE041",request);
        }
        return new ResponseHandler().handleResponse("Verifikasi Berhasil",HttpStatus.OK,mapResponse,null,request);
    }

    /** proses input password baru untuk 051-060*/
    public ResponseEntity<Object> lupaPasswordStepThree(User user, HttpServletRequest request){
        String strTokenEstafet = "";
        try {

            /** membandingkan password baru dengan konfirmasi password baru yang di entry oleh user*/
            if(!user.getPassword().equals(user.getPasswordConfirmation())) {
                return new ResponseHandler().handleResponse("Password Tidak Sama",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV051",request);
            }
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if(opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User Belum Terdaftar",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV052",request);
            }
            User userNext = opUser.get();
            /** menggunakan equals karena hash nya yang dikirim ke client
             * menghemat sumber daya (penggunaan memory)
             */
            Boolean isValid = user.getTokenEstafet().equals(userNext.getTokenEstafet());
            if(!isValid) {
                return new ResponseHandler().handleResponse("Request Tidak Valid",HttpStatus.BAD_REQUEST,null,
                        "TRN00FV053",request);
            }
            strTokenEstafet = BcryptImpl.hash(String.valueOf(random.nextInt(99)));

            /** Token Estafet tetap di update untuk keamanan, agar token sebelumnya tidak bisa digunakan lagi di proses ini
             */
            userNext.setTokenEstafet(strTokenEstafet);
            userNext.setPassword(BcryptImpl.hash(userNext.getUsername()+user.getPassword()));
        }catch (Exception e){
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE051",request);
        }
        return new ResponseHandler().handleResponse("Password Berhasil Diubah",HttpStatus.OK,null,null,request);
    }

    public ResponseEntity<Object> refreshToken(User user, HttpServletRequest request) {
        Map<String,Object> m = new HashMap<>();
        User userNext = null;
        try{
            String username = user.getUsername();
            Optional<User> opUser = userRepo.findByUsernameOrEmailOrNoHpAndIsRegistered(username,username,username,true);
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan",HttpStatus.BAD_REQUEST,null,"TRN00FV021",request);
            }
            userNext = opUser.get();//diambil dari DB

            String pwdDB = userNext.getUsername()+user.getPassword();
            if(!BcryptImpl.verifyHash(pwdDB,userNext.getPassword())) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!",HttpStatus.BAD_REQUEST,null,"TRN00FV022",request);
            }
        }catch (Exception e){
            LoggingFile.logException("AuthService","login(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "TRN00FE021",request);
        }

        /** SET PAYLOAD JWT */
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("em",userNext.getEmail());
        mapData.put("id",userNext.getId());
        mapData.put("hp",userNext.getNoHp());
        mapData.put("naleng",userNext.getNamaLengkap());
        String token = jwtUtility.doGenerateToken(mapData,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token", token);

        return new ResponseHandler().handleResponse("Login Berhasil !!",HttpStatus.OK,m,null,request);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsernameAndIsRegistered(username,true);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException("Username atau Password Salah !!!");
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
    }

    public User mapToUser(RegisDTO regisDTO) {
        User user = new User();
        user.setEmail(regisDTO.getEmail());
        user.setNoHp(regisDTO.getNoHp());
        user.setAlamat(regisDTO.getAlamat());
        user.setNamaLengkap(regisDTO.getNamaLengkap());
        user.setPassword(regisDTO.getPassword());
        user.setTanggalLahir(regisDTO.getTanggalLahir());
        user.setUsername(regisDTO.getUsername());

        return user;
    }

    public User mapToUser(VerifyRegisDTO verifyRegisDTO) {
        User user = new User();
        user.setEmail(verifyRegisDTO.getEmail());
        user.setOtp(verifyRegisDTO.getOtp());

        return user;
    }

    public User mapToUser(LoginDTO loginDTO) {
        User user = new User();
        user.setUsername(loginDTO.getUsername());
        user.setPassword(loginDTO.getPassword());

        return user;
    }


    public User mapToUserMapper(VerifyRegisDTO verifyRegisDTO) {
            return  modelMapper.map(verifyRegisDTO, User.class);
    }

    public User mapToUserMapper(RegisDTO regisDTO) {
        return  modelMapper.map(regisDTO, User.class);
    }

    public User mapToUserMapper(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public List<MenuLoginDTO> mapToMenuLoginDTO(List<Menu> ltMenu){
        List<MenuLoginDTO> ltMenuDTO = new ArrayList<>();
        for (Menu menu : ltMenu) {
            MenuLoginDTO menuDTO = new MenuLoginDTO();
            menuDTO.setNama(menu.getNama());
            menuDTO.setPath(menu.getPath());
            menuDTO.setNamaGroupMenu(menu.getGroupMenu().getNama());
            ltMenuDTO.add(menuDTO);
        }

        return ltMenuDTO;
    }
}