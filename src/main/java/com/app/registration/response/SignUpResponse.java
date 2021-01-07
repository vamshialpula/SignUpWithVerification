package com.app.registration.response;

import com.app.registration.model.OTPDetails;
import com.app.registration.model.User;

public class SignUpResponse {

    private User user;
    private OTPDetails otpDetails;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OTPDetails getOtpDetails() {
        return otpDetails;
    }

    public void setOtpDetails(OTPDetails otpDetails) {
        this.otpDetails = otpDetails;
    }
}
