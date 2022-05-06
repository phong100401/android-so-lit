package com.t2004eandroid.service.impl;

import static org.junit.Assert.*;

import com.t2004eandroid.service.LyricService;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class LyricDotComServiceTest {

    @Test
    public void searchSongByName() throws IOException {
        LyricService lyricService = new LyricDotComService();
        List<String> listLink = lyricService.searchSongByName("someone like you");
        for (String link :
                listLink) {
            System.out.println(link);
        }
    }

    @Test
    public void getSongLyricByLink() throws IOException {
        String link = "https://www.lyrics.com/lyric/24040892/Adele/Someone+Like+You";
        LyricService lyricService = new LyricDotComService();
        String htmlLyric = lyricService.getSongLyricByLink(link);
        System.out.println(htmlLyric);
    }
}