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

public class KakaoLogin extends AccessTokenOAuthLogin {
    private String clientId;
    private String redirectUri;
    private String accessTokenUri;
    private String profileUri;
    private String loginRequestUri;

    @Override
    public HttpEntity getAccessTokenRequestHttpEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        return new HttpEntity<>(params, headers);
    }

    @Override
    public OAuthUserProfile profileParser(ResponseEntity<String> response) throws Exception {
        OAuthUserProfile snsUser;

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
            JSONObject profile = (JSONObject) kakaoAccount.get("profile");

            snsUser = OAuthUserProfile.of(
                getOAuthVendorType()+"_"+jsonObject.get("id"),
                profile.get("nickname").toString(),
                kakaoAccount.get("email").toString(),
                profile.get("profile_image_url").toString());

        } catch (ParseException e) {
            throw new RuntimeException("카카오 로그인을 통해 프로필 정보를 가져오는데 실패했습니다.");
        }

        return snsUser;
    }

    @Override
    public OAuthUserProfile requestUserProfileByToken(String token) {
        try {
            return this.requestUserProfileByAccessToken(token, profileUri);

        } catch(Exception e){
            throw new RuntimeException("카카오 로그인을 통해 프로필 정보를 가져오는데 실패했습니다.");
        }

    }

    @Override
    public OAuthVendorType getOAuthVendorType() {
        return OAuthVendorType.KAKAO;
    }
}
