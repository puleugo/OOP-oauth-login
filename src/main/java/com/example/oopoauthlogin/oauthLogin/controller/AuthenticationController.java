package com.example.oopoauthlogin.oauthLogin.controller;

import com.example.oopoauthlogin.oauthLogin.serivce.AuthenticationService;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthVendorType;
import com.example.oopoauthlogin.oauthLogin.domain.User;
import org.springframework.http.HttpEntity;

public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public HttpEntity<User> loginUser(String token, OAuthVendorType oAuthVendorType){
        OAuthUserProfile userProfile =authenticationService.oauthLoginUser(token, oAuthVendorType);

        User user = authenticationService.findByUserProfileId(userProfile.getId());

        if (user == null) {
            User registeredUser = authenticationService.registerUserByUserProfile(userProfile);
            return new HttpEntity<>(registeredUser);
        }
        return new HttpEntity<>(user);
    }
}
