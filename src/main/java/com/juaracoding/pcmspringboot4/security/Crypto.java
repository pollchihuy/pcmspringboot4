package com.juaracoding.pcmspringboot4.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

/**
 * Class implementasi fungsional enkripsi dengan Algoritma AES
 */
public class Crypto {

//    private static final String defaultKey = "4d05ac36ce72f080dc9e29af7d03bf0dd15a530305415c811a6a64767108145d";
//    private static final String defaultKey = "7cf79176bfc48d17ea3c84d26399404244a4f0d3d3e2d8c01d5a6d9b37bbf7e8";
    private static final String defaultKey = "2858b4dd4d9e90e713698facaa7a79450a0eb750792759c1c10637d2cad25ecd";

    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {
        String strToEncrypt = "jdbc:sqlserver://host.docker.internal;encrypt=true;trustServerCertificate=true;databaseName=BEB26;schema=dbo";//put text to encrypt in here
        System.out.println("Encryption Result : "+performEncrypt(strToEncrypt));

        String strToDecrypt = "7bea6aa05c93c2ca7c35fee6219e4ec0d407da64e6739e5df6b0d76db25041a5208e0dab4ae7c91ff3048ac19768015ba133ee06e99d0abccc227254bddbad6cc5cda09a60355200af317e945f5ce9a43ff832a2e5e028418395c11cbdaa4154edb142ba95389824dd142d094f1026d3";//put text to decrypt in here
        System.out.println("Decryption Result : "+performDecrypt(strToDecrypt));
    }
}