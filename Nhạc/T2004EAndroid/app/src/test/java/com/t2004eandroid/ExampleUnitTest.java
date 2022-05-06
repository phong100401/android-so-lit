package com.t2004eandroid;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.t2004eandroid.dto.LoginDto;
import com.t2004eandroid.entity.Account;
import com.t2004eandroid.entity.Song;
import com.t2004eandroid.service.AccountService;
import com.t2004eandroid.util.RetrofitGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Account account = new Account();
        account.setFirstName("A");
        account.setLastName("Nguyen");
        account.setBirthday("2001-12-30");
        account.setAddress("Ha Noi");
        account.setAvatar("https://123.jpg");
        account.setEmail("a1234566778@gmail.com");
        account.setGender(0);
        account.setPhone("1234567890");
        AccountService accountService = RetrofitGenerator.createService(AccountService.class);
        System.out.println(new Gson().toJson(account));
        try {
            Response<Account> response = accountService.register(account).execute();
            if(response.isSuccessful()){
                System.out.println(new Gson().toJson(response.body()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testListSong() throws IOException {
        AccountService accountService = RetrofitGenerator.createService(AccountService.class);
        List<Song> song = accountService.getSong().execute().body();
        for (Song s :
                song) {
            System.out.println(s.toString());
        }
    }
}