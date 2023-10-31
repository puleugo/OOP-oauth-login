package com.example.oopoauthlogin.oauthLogin.serivce;

import com.example.oopoauthlogin.oauthLogin.dao.UserDao;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthUserProfile;
import com.example.oopoauthlogin.oauthLogin.domain.OAuthVendorType;
import com.example.oopoauthlogin.oauthLogin.domain.User;

import java.util.List;

public class AuthenticationService {
    private final List<OAuthLogin> oauthLoginService;
    private final UserDao userDao;

    public AuthenticationService(UserDao userDao, List<OAuthLogin> oauthLoginService) {
        this.oauthLoginService = oauthLoginService;
        this.userDao = userDao;
    }

    public OAuthUserProfile oauthLoginUser(String token, OAuthVendorType oAuthVendorType){
        OAuthLogin loginService = injectLoginService(oAuthVendorType);
        OAuthUserProfile userProfile = loginService.requestUserProfileByToken(token);
        return userProfile;
    }

    private OAuthLogin injectLoginService(OAuthVendorType oAuthVendorType){
        for (OAuthLogin loginService : oauthLoginService) {
            if (loginService.getOAuthVendorType() == oAuthVendorType) {
                return loginService;
            }
        }
        throw new RuntimeException("로그인 서비스를 찾을 수 없습니다.");
    }

    public User findByUserProfileId(String id) {
        return userDao.findByUserProfileId(id).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public User registerUserByUserProfile(OAuthUserProfile userProfile) {
        User user = User.of(userProfile);
        userDao.save(user);
        return user;
    }
}
