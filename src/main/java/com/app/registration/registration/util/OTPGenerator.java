package com.app.registration.util;

import java.util.SplittableRandom;

public class OTPGenerator {
    public static String generateOTP() {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder otp = new StringBuilder();

        for(int i=0; i<4;i++) {
            otp.append(splittableRandom.nextInt(0,10));
        }
        return otp.toString();
    }


    /*public static String generateOTP(int otpLength, long currentTimeMillis) {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder otp = new StringBuilder();

        for(int i=0; i<otpLength;i++) {
            otp.append(splittableRandom.nextInt(0,10));
        }
        return otp.toString();
    }*/
}
