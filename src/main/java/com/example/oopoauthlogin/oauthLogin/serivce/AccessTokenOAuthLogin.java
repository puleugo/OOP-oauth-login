package com.example.oopoauthlogin.oauthLogin.serivce;

import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpMethod;

public abstract class AccessTokenOAuthLogin implements OAuthLogin{

    protected OAuthUserProfile requestUserProfileByAccessToken(String token, String profileUri) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();

        if (token.charAt(0) == '"') {
            token = token.substring(1, token.length() - 1);
        }

        headers.add("Authorization", "Bearer " + token);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
            profileUri,
            HttpMethod.GET,
            request,
            String.class
        );

        OAuthUserProfile snsUser = this.profileParser(response);
        if (snsUser.getId() == null || snsUser.getName() == null || snsUser.getPictureUrl() == null) {
            throw new RuntimeException("프로필 정보를 가져오 는데 실패했습니다.");
        }

        return snsUser;
    }

    protected String requestAccessToken(String code, String accessTokenUri) {
        return "";
    }

    abstract OAuthUserProfile profileParser(ResponseEntity<String> response) throws Exception;
    abstract HttpEntity getAccessTokenRequestHttpEntity(String code);

}
