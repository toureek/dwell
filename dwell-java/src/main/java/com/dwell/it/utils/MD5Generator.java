package com.dwell.it.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {


    // PRNG Algorithm Best Practise on StackOverFlow
    // ref to https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string

    /**
     * 根据输入文本 产生一个16字节大小的MD5值
     *
     * @param stringText 输入文本
     * @return MD5_id
     */
    public static String generateMD5Identifier(String stringText) {
        String generated_MD5_id = null;
        try {                                           // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringText.getBytes());
            byte[] bytes = md.digest();                 //Get the hash's bytes
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generated_MD5_id = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return generated_MD5_id;
    }
}
