package com.example.nutrionall.models.User;

import java.io.Serializable;

public class AuthUser implements Serializable {
    String _id;
    String name;
    String email;
    Boolean isPremium;
    String token;
    Boolean validToken;
    String urlImg;

    public Boolean getValidToken() {
        return validToken;
    }

    public void setValidToken(Boolean validToken) {
        this.validToken = validToken;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.token = urlImg;
    }
}