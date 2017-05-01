package com.dfish.cryptomessenger;


import android.util.Base64;

import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by duncanfisher on 4/26/17.
 */

public class Crypter {

    static final private String ALGO = "AES";
    static final private int BITS = 128;
    static final private String CIPHER = "AES/CBC/PKCS5PADDING";


    public static byte[] encrypt(byte[] iv, byte[] plaintext, SecretKey key) {
        byte[] ciphertext = null;
        try {
            IvParameterSpec spec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            ciphertext = cipher.doFinal(plaintext);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    public static byte[] decrypt(byte[] iv, byte[] ciphertext, SecretKey key) {

        byte[] plaintext = null;
        try {
            IvParameterSpec spec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            plaintext = cipher.doFinal(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plaintext;
    }

    public static String encryptWrap(String plaintext, SecretKey key) {
        byte[] iv = new byte[16];
        Arrays.fill(iv, (byte)0x00);
        return Base64.encodeToString(encrypt(iv, plaintext.getBytes(), key), Base64.DEFAULT);
    }

    public static String decryptWrap(String ciphertext, SecretKey key) {
        byte[] iv = new byte[16];
        Arrays.fill(iv, (byte)0x00);
        return new String(decrypt(iv, Base64.decode(ciphertext, Base64.DEFAULT), key)) ;
    }



    public static SecretKey generateKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGO);
            keyGenerator.init(BITS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyGenerator.generateKey();
    }
}
