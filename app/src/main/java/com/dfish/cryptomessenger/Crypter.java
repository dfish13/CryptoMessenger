package com.dfish.cryptomessenger;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by duncanfisher on 4/26/17.
 */

public class Crypter {

    static final private String KEY = "4KiYxG6LYLXnuuWL";

    public static String encrypt(String key, String input)
        throws UnsupportedEncodingException,
            GeneralSecurityException {

        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("UTF-8"));

        SecretKey secretKey = deriveKey(key, KEY);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] encrypted = cipher.doFinal(input.getBytes());

        return Base64.encodeToString(encrypted, Base64.DEFAULT);

    }

    public static String decrypt(String key, String input)
            throws UnsupportedEncodingException,
            GeneralSecurityException {

        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("UTF-8"));

        SecretKey secretKey = deriveKey(key, KEY);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] original = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));

        return new String(original);
    }



    private static SecretKey deriveKey(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        char[] passwordChars = new char[password.length()];
        password.getChars(0, password.length(), passwordChars, 0);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passwordChars, salt.getBytes(), 2000, 256);

        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
