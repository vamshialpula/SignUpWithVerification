package com.app.registration.service;

import com.app.registration.constants.Constants;
import com.app.registration.model.OTPDetails;
import com.app.registration.model.User;
import com.app.registration.request.SignUpRequest;
import com.app.registration.response.Response;
import com.app.registration.response.SignUpResponse;
import com.app.registration.respository.OTPDetailsRepository;
import com.app.registration.respository.UserRepository;
import com.app.registration.util.OTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    OTPDetailsRepository otpDetailsRepository;



    public Response signUpUser(SignUpRequest request){
        try {
            User user = userRepository.findByEmail(request.getEmail());
            if(user == null){
                user = prepareUser(request);
                userRepository.save(user);
            }
            invalidateOldOTP(user);
            String otp = prepareOTPForUser();
            OTPDetails otpDetails = prepareOTPDetails(otp, user);
            otpDetailsRepository.save(otpDetails);
            SignUpResponse res = new SignUpResponse();
            res.setUser(user);
            res.setOtpDetails(otpDetails);
            logger.info("User created..!");
            return new Response(200, "User successfully registered..!", res);
        }catch(Exception e){
            logger.warn("Problem while creating user..!",e.getMessage());
        }
        return null;
    }

    private void invalidateOldOTP(User user) {
        OTPDetails otp = otpDetailsRepository.findByUserAndStatus(user, Constants.OTP_SENT);
        if(otp != null) {
            otp.setStatus(Constants.OTP_EXPIRED);
            otpDetailsRepository.save(otp);
        }
    }

    private OTPDetails prepareOTPDetails(String otp, User user) {
        OTPDetails otpdetails = new OTPDetails();
        otpdetails.setOtp(otp);
        otpdetails.setStatus(Constants.OTP_SENT);
        otpdetails.setCreatedAt(new Date());
        otpdetails.setUser(user);
        return otpdetails;
    }

    private String prepareOTPForUser() {
        return OTPGenerator.generateOTP();
    }

    private User prepareUser(SignUpRequest request) {
        User user  =  new User();
        user.setStatus(Constants.USER_INACTIVE);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return user;
    }

    public Response verifyAndActivate(Long userId, String otp) {
        try{
            User user = userRepository.findByUserId(userId);
            if(user != null){
                OTPDetails otpDetails = otpDetailsRepository.findByUserAndStatus(user, Constants.OTP_SENT);
                if(otpDetails.getOtp().equals(otp)){
                    otpDetails.setStatus(Constants.OTP_VERIFIED);
                    otpDetailsRepository.save(otpDetails);
                    user.setStatus(Constants.USER_ACTIVE);
                    userRepository.save(user);
                    SignUpResponse res = new SignUpResponse();
                    res.setOtpDetails(otpDetails);
                    res.setUser(user);
                    return new Response(200, "OTP successfully verified and Activated the User...!", res);
                }
                return new Response(500, "Invalid OTP..!", null);
            }
            return new Response(500, "User not found with that ID..!", null);
        }catch (Exception e){
            logger.warn("Problem while verifying otp..!",e.getMessage());
        }
    return null;
    }

    public Response getActiveUsers() {
        try {
            List<User> activeUsers = new ArrayList<User>();
            activeUsers = userRepository.findAllByStatus(Constants.USER_ACTIVE);
            if(!activeUsers.isEmpty()) {
                return new Response(200, "Retrieved active user list....!", activeUsers);
            }
        } catch (Exception e) {
            logger.warn("Problem while retrieving active users..!",e.getMessage());
        }
        return new Response(500, "No active user found..!", null);
    }

    public Response getUserDetails(Long userId) {
        try {
            User user = userRepository.findByUserId(userId);
            if(user != null) {
                return new Response(200, "User found with that ID....!", user);
            }
        } catch (Exception e) {
            logger.warn("User details not found..!",e.getMessage());
        }
        return new Response(500, "No user found with that ID..!", null);
    }
}
