package com.thedariusz.warnme.api;

import com.thedariusz.warnme.MeteoAlert;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Post {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String title;
    private String date;
    private String description;
    private String level;
    private List<String> images;
    private Set<String> categories;

    public Post() {
    }

    public Post(String title, String date, String description, String level, List<String> images, Set<String> categories) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.level = level;
        this.images = images;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public static List<Post> preparePosts(List<MeteoAlert> meteoAlerts) {
        List<Post> posts = new ArrayList<>();
        meteoAlerts
                .forEach(meteoAlert -> {
                    Post post = new Post();
                    post.date = "post date: " + formatDate(meteoAlert.getCreationDate());
                    post.description = meteoAlert.getDescription();
                    post.images = meteoAlert.getMedia();
                    post.title = prepareTitleBasedOnCategories(meteoAlert.getCategories());
                    post.level = Integer.toString(meteoAlert.getLevel());
                    post.categories = meteoAlert.getCategories();
                    posts.add(post);
                });

        return posts;
    }

    private static String prepareTitleBasedOnCategories(Set<String> categories) {
        if (categories.isEmpty()) {
            return "Ostrzeżenie";
        }

        return categories.toArray()[0].toString().toUpperCase();
    }

    private static String formatDate(String creationDate) {
        TemporalAccessor dateParsed = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(creationDate);
        return DATE_TIME_FORMATTER.format(dateParsed);
    }
}
