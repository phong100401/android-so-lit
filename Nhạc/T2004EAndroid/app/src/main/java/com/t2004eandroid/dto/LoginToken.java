package com.t2004eandroid.dto;

public class LoginToken {
    private String access_token;
    private String refresh_token;
    private int account_id;

    public LoginToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
