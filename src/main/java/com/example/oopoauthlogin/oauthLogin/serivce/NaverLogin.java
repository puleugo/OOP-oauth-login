package com.example.oopoauthlogin.oauthLogin.serivce;

import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthVendorType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NaverLogin extends AccessTokenOAuthLogin{
    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private String profileUri;

    @Override
    public OAuthUserProfile requestUserProfileByToken(String token) {
        try {
            return this.requestUserProfileByAccessToken(token, profileUri);

        } catch(Exception e){
            throw new RuntimeException("네이버 로그인을 통해 프로필 정보를 가져오는데 실패했습니다.");
        }
    }

    @Override
    public HttpEntity getAccessTokenRequestHttpEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        headers.add("Content-type", "application/x-www-form-urlencoded");

        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);

        return new HttpEntity<>(params, headers);
    }

    public OAuthVendorType getOAuthVendorType() {
        return OAuthVendorType.NAVER;
    }

    public OAuthUserProfile profileParser(ResponseEntity<String> response) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            JSONObject userProfile = (JSONObject) jsonObject.get("response");

            return OAuthUserProfile.of(
                getOAuthVendorType() + "_" + userProfile.get("id").toString(),
                userProfile.get("nickname").toString(),
                userProfile.get("email").toString(),
                userProfile.get("profile_image").toString()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
