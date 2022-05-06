package com.t2004eandroid.service;

import static org.junit.Assert.*;

import com.t2004eandroid.entity.Song;
import com.t2004eandroid.util.RetrofitGenerator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SongServiceTest {

    private SongService songService;

    @Before
    public void setUp() throws Exception {
        songService = RetrofitGenerator.createService(SongService.class);
    }

    @Test
    public void getSong() throws IOException {
        Response<List<Song>> listResponse = songService.getSong().execute();
        if (listResponse.isSuccessful()) {
            List<Song> listSong = listResponse.body();
            for (Song song :
                    listSong) {
                System.out.println(song.toString());
            }
        }
    }
}