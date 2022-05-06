package com.t2004eandroid.service;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.t2004eandroid.dto.CredentialDto;
import com.t2004eandroid.dto.LoginDto;
import com.t2004eandroid.dto.LoginToken;
import com.t2004eandroid.entity.Account;
import com.t2004eandroid.util.RetrofitGenerator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Response;

public class AccountServiceTest {

    AccountService accountService;
    @Before
    public void setUp() throws Exception {
        accountService = RetrofitGenerator.createService(AccountService.class);
    }

    @Test
    public void register() {

        Account account = new Account();
        account.setFirstName("Xuan Hung");
        account.setLastName("Dao");
        account.setAvatar("http://avar.png");
        account.setPhone("0912332");
        account.setAddress("Hanoi");
        account.setGender(1);
        account.setBirthday("2004-10-20");
        account.setEmail("hongluyen00001@gmail.com");
        account.setPassword("123");
        try {
            Account savedAccount = accountService.register(account).execute().body();
            System.out.println(savedAccount.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testRegister() {
        LoginDto login = new LoginDto();
        login.setEmail("hongluyen00001@gmail.com");
        login.setPassword("123");
        try {
            Response<LoginToken> credentialDtoResponse = accountService.login(login).execute();
            System.out.println(credentialDtoResponse.code());
            System.out.println(credentialDtoResponse.isSuccessful());
            System.out.println(credentialDtoResponse.toString());
            System.out.println(new Gson().toJson(credentialDtoResponse) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}