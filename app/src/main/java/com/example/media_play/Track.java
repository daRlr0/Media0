package com.example.media_play;

public class Track {
    private int resourceId; // ID ресурса аудиофайла
    private String title;
    private String artist;
    private int albumArtId; // ID ресурса изображения обложки

    public Track(int resourceId, String title, String artist, int albumArtId) {
        this.resourceId = resourceId;
        this.title = title;
        this.artist = artist;
        this.albumArtId = albumArtId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getAlbumArtId() {
        return albumArtId;
    }
}

