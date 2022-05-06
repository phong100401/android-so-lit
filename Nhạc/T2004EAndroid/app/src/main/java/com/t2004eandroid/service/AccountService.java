package com.t2004eandroid.service;

import com.t2004eandroid.dto.CredentialDto;
import com.t2004eandroid.dto.LoginDto;
import com.t2004eandroid.dto.LoginToken;
import com.t2004eandroid.entity.Account;
import com.t2004eandroid.entity.Song;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface AccountService {
    @POST("/api/v1/accounts")
    Call<Account> register(@Body Account account);

    @GET("/api/v1/songs")
    Call<List<Song>> getSong();

    @POST("/api/v1/accounts/authentication")
    Call<LoginToken> login(@Body LoginDto login);
}
