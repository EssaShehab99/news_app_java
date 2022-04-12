package com.example.news_app_java;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class News {
    private String id;
    private String title;
    private String details;
    private String imageUrl;

    public News(String id, String title, String details, String imageUrl) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.imageUrl = imageUrl;
    }

    public News() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public News fromMap(Map<String, Object> map, String id) throws ParseException {
        return new News(
                id,
                Objects.requireNonNull(map.getOrDefault("title","")).toString(),
                Objects.requireNonNull(map.getOrDefault("details", "")).toString(),
                Objects.requireNonNull(map.getOrDefault("imageUrl", "")).toString()
        );
    }

    public final Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("title", title);
            put("details", details);
            put("imageUrl", imageUrl);
        }};
    }
}
