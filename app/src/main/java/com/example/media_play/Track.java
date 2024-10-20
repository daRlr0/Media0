package com.example.media_play;

public class Track {
    private int resourceId; // ID ресурса аудиофайла
    private String title; // Название песни
    private String artist; // Имя исполнителя

    // Конструктор
    public Track(int resourceId, String title, String artist) {
        this.resourceId = resourceId;
        this.title = title;
        this.artist = artist;
    }

    // Геттеры
    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}

