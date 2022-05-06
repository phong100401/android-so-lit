package com.t2004eandroid.service;

import java.io.IOException;
import java.util.List;

public interface LyricService {
    List<String> searchSongByName(String name) throws IOException;
    String getSongLyricByLink(String link) throws IOException;
}
