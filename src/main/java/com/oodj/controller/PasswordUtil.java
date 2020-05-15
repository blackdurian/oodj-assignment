package com.oodj.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(
                    "SHA-256");   // Static getInstance method is called with hashing SHA
            byte[] messageDigest = md.digest(input.getBytes()); // and return array of byte
            BigInteger no = new BigInteger(1,
                    messageDigest);  // Convert byte array into signum representation
            StringBuilder hashedText = new StringBuilder(no.toString(16));// Convert message digest into hex value
            while (hashedText.length() < 32) {
                hashedText.insert(0, "0");
            }
            return hashedText.toString();
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
