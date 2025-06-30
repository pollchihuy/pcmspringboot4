package com.juaracoding.pcmspringboot4.security;

import java.util.function.Function;

public class BcryptImpl {

    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password,
                                              String hash,
                                              Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password , String hash)
    {
        return bcrypt.verifyHash(password,hash);
    }
    
    public static void main(String[] args) {
        String strUserName = "Achmadi@4321";
        String hashKeDB = hash(strUserName);
        System.out.println("Ini Hash Ke DB: " + hashKeDB);
//        $2a$11$TdAcTD81TDQGSGfL4AzJu.QT5QYNfAZqr7r.vwSbTjBbrXyeUConS
//        $2a$11$enqSTycH1eGpgR/oo7arwu48V0ceVLaXHJ/W/.bVzwhZ6zTFK6JP6
//        $2a$11$4LVzI8HCzoRD8/.CJ7vFCe2Ms6BFZNbEYkgvkCwIJahWJ7IBSC3Ca
//        System.out.println("Compare Hash 1 : "+verifyHash("Achmadi@1234","$2a$11$TdAcTD81TDQGSGfL4AzJu.QT5QYNfAZqr7r.vwSbTjBbrXyeUConS"));
//        System.out.println("Compare Hash 2 : "+verifyHash("Achmadi@1234","$2a$11$enqSTycH1eGpgR/oo7arwu48V0ceVLaXHJ/W/.bVzwhZ6zTFK6JP6"));
//        System.out.println("Compare Hash 3 : "+verifyHash("Achmadi@1234","$2a$11$4LVzI8HCzoRD8/.CJ7vFCe2Ms6BFZNbEYkgvkCwIJahWJ7IBSC3Ca"));

    }
}