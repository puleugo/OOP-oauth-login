package com.example.oopoauthlogin.oauthLogin.serivce;


import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthVendorType;

public interface OAuthLogin {
    OAuthUserProfile requestUserProfileByToken(String token);
    OAuthVendorType getOAuthVendorType();

}
