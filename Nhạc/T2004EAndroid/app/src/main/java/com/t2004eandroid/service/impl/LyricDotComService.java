package com.t2004eandroid.service.impl;

import com.t2004eandroid.service.LyricService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LyricDotComService implements LyricService {

    private static final String BASE_DOMAIN = "https://www.lyrics.com";
    private static final String BASE_URL = "https://www.lyrics.com/lyrics";

    @Override
    public List<String> searchSongByName(String name) throws IOException {
        List<String> listLink = new ArrayList<>();
        name.getBytes(StandardCharsets.UTF_8);
        Connection.Response connecResponse = Jsoup.connect(BASE_URL + "/" + name).method(Connection.Method.GET).execute();
        if (connecResponse.statusCode() == 200) {
            Document document = connecResponse.parse();
            Elements elements = document.select(".best-matches>.bm-case>.bm-label>b>a[href]");
            if (elements.size() > 0) {
                for (Element e :
                        elements) {
                    String path = e.attr("href");
                    if (!path.contains(BASE_DOMAIN)) {
                        path = BASE_DOMAIN + e.attr("href");
                    }
                    listLink.add(path);
                }
            }
        }
        return listLink;
    }

    @Override
    public String getSongLyricByLink(String link) throws IOException {
        Connection.Response connecResponse = Jsoup.connect(link).method(Connection.Method.GET).execute();
        if (connecResponse.statusCode() == 200) {
            Document document = connecResponse.parse();
            Element element = document.selectFirst("pre#lyric-body-text");
            if (element != null) {
                return element.html();
            }
        }
        return null;
    }
}
