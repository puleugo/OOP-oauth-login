package com.example.oopoauthlogin.oauthLogin.domain;

public class OAuthUserProfile {
    private String id;
    private String name;
    private String email;
    private String pictureUrl;

    public OAuthUserProfile(String id, String name, String email, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    private OAuthUserProfile() {
    }
    public static OAuthUserProfile of(String id, String name, String email, String pictureUrl) {
        return new OAuthUserProfile(id, name, email, pictureUrl);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
