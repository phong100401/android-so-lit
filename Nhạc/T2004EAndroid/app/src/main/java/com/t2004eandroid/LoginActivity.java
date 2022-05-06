package com.t2004eandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.t2004eandroid.dto.LoginDto;
import com.t2004eandroid.dto.LoginToken;
import com.t2004eandroid.entity.Account;
import com.t2004eandroid.service.AccountService;
import com.t2004eandroid.util.RetrofitGenerator;

import java.io.IOException;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail, etPassword;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        initData();
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                LoginDto loginDto = new LoginDto();
                loginDto.setEmail(email);
                loginDto.setPassword(password);
                try {
                    Response<LoginToken> tokenResponse =  accountService.login(loginDto).execute();
                    if(tokenResponse.isSuccessful()){
                        LoginToken loginToken = tokenResponse.body();
                        Log.d("Token", loginToken.getAccess_token());
                    }
                    //U857HXAfXKOobJrA299l7gPr9pHbdXEThUDzOeKlmavKamy8LHASoKLaOMdUw0ioqhVOsNBKdb261FQQ1648217471
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initData(){
        etEmail = (EditText) findViewById(R.id.login_email);
        etPassword = (EditText) findViewById(R.id.login_pass);
        accountService = RetrofitGenerator.createService(AccountService.class);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
}