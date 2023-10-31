package com.example.oopoauthlogin.oauthLogin.serivce;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthVendorType;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

public class GoogleLogin implements OAuthLogin{
    private String GOOGLE_CLIENT_ID;

    public OAuthUserProfile requestUserProfileByToken(String token) {
        GoogleIdToken claims = verifyToken(token);
        return OAuthUserProfile.of(
            getOAuthVendorType() + "_" + claims.getPayload().getSubject(),
            claims.getPayload().getEmail(),
            claims.getPayload().getEmail(),
            claims.getPayload().get("picture").toString()
        );
    }

    private GoogleIdToken verifyToken(String idToken) {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);

            if(googleIdToken == null) {
                throw new RuntimeException("구글 로그인을 통해 프로필 정보를 가져오는데 실패했습니다.");
            }

            return googleIdToken;

        } catch (Exception e){
            throw new RuntimeException("구글 로그인을 통해 프로필 정보를 가져오는데 실패했습니다.");
        }
    }


    @Override
    public OAuthVendorType getOAuthVendorType() {
        return OAuthVendorType.GOOGLE;
    }
}
