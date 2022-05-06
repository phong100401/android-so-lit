package com.t2004eandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.t2004eandroid.entity.Account;
import com.t2004eandroid.service.AccountService;
import com.t2004eandroid.util.RetrofitGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etFirstname, etLastname, etPassword, etAddress, etPhone, etEmail, etAvatar, etBirthday;
    private CheckBox cbMale, cbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    private void initData(){
        etFirstname = (EditText) findViewById(R.id.firstname);
        etLastname = (EditText) findViewById(R.id.lastname);
        etAddress = (EditText) findViewById(R.id.address);
        etPassword = (EditText) findViewById(R.id.password);
        etPhone = (EditText) findViewById(R.id.phone);
        etEmail = (EditText) findViewById(R.id.email);
        etAvatar = (EditText) findViewById(R.id.avatar);
        etBirthday = (EditText) findViewById(R.id.dob);
        cbMale = (CheckBox) findViewById(R.id.male);
        cbFemale = (CheckBox) findViewById(R.id.female);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    private void initListener(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                String address = etAddress.getText().toString();
                String password = etPassword.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String avatar = etAvatar.getText().toString();
                String birthday = etBirthday.getText().toString();

                Account account = new Account();
                account.setFirstName(firstname);
                account.setLastName(lastname);
                account.setAddress(address);
                account.setPassword(password);
                account.setPhone(phone);
                account.setEmail(email);
                account.setAvatar(avatar);
                account.setBirthday(birthday);

                //check box
                if (cbMale.isChecked()){
                    account.setGender(1);
                }
                if (cbFemale.isChecked()){
                    account.setGender(0);
                }
                AccountService accountService = RetrofitGenerator.createService(AccountService.class);
                Log.d("ERROR", new Gson().toJson(account));
                Response<Account> accountCall = null;
                try {
                    accountCall = accountService.register(account).execute();
                    if(accountCall.isSuccessful()){
                        CharSequence charSequence = "Tạo thành công";
                        Toast toast = Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    CharSequence charSequence = e.getMessage();
                    Toast toast = Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private boolean checkValidate(){
        return true;
    }

}