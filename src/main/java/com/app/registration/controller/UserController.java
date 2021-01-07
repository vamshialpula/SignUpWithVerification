package com.app.registration.controller;

import com.app.registration.request.SignUpRequest;
import com.app.registration.response.Response;
import com.app.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody SignUpRequest request) {
        Response response = userService.signUpUser(request);
        if(response != null){
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(500).body(new Response(500, "Failed to create user", null));
    }

    @RequestMapping(value = "/verify_activate", method = RequestMethod.POST)
    public ResponseEntity<?> activateUser(@RequestParam Long userId,@RequestParam String otp) {
        Response response = userService.verifyAndActivate(userId, otp);
        if(response != null){
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(500).body(new Response(500, "Failed to activate user", null));
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseEntity<?> activeUsers() {
        Response response = userService.getActiveUsers();
        if(response != null){
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(500).body(new Response(500, "Failed to retrieve active users", null));
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        Response response = userService.getUserDetails(userId);
        if(response != null){
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(500).body(new Response(500, "Failed to get user info", null));
    }

}
