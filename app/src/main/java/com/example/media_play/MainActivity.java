package com.example.media_play;
import android.widget.ImageView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton playPauseButton, nextButton, previousButton;
    private SeekBar seekBar;
    private TextView songTitle, artistName, startTime, endTime;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable updateSeekBar;

    private List<Track> trackList;
    private int currentTrackIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playPauseButton = findViewById(R.id.btn_play_pause);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        seekBar = findViewById(R.id.seek_bar);
        songTitle = findViewById(R.id.song_title);
        artistName = findViewById(R.id.artist_name);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);


        trackList = new ArrayList<>();
        trackList.add(new Track(R.raw.coldplay, "Название Песни 1", "Исполнитель 1", R.drawable.bar1));
        trackList.add(new Track(R.raw.madcon, "Название Песни 2", "Исполнитель 2", R.drawable.bar2));
        trackList.add(new Track(R.raw.indila, "Название Песни 3", "Исполнитель 3", R.drawable.bar3));



        mediaPlayer = MediaPlayer.create(this, trackList.get(currentTrackIndex).getResourceId());
        seekBar.setMax(mediaPlayer.getDuration());
        endTime.setText(formatTime(mediaPlayer.getDuration()));


        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    startTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.postDelayed(updateSeekBar, 1000);


        songTitle.setText(trackList.get(currentTrackIndex).getTitle());
        artistName.setText(trackList.get(currentTrackIndex).getArtist());


        playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playPauseButton.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                playPauseButton.setImageResource(R.drawable.ic_pause);
            }
        });


        nextButton.setOnClickListener(v -> {
            currentTrackIndex = (currentTrackIndex + 1) % trackList.size();
            playNewTrack();
        });


        previousButton.setOnClickListener(v -> {
            currentTrackIndex = (currentTrackIndex - 1 + trackList.size()) % trackList.size();
            playNewTrack();
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    private void playNewTrack() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, trackList.get(currentTrackIndex).getResourceId());
        seekBar.setMax(mediaPlayer.getDuration());
        endTime.setText(formatTime(mediaPlayer.getDuration()));
        mediaPlayer.start();
        playPauseButton.setImageResource(R.drawable.ic_pause);

        // Обновляем текст
        songTitle.setText(trackList.get(currentTrackIndex).getTitle());
        artistName.setText(trackList.get(currentTrackIndex).getArtist());

        // Обновляем обложку альбома
        ImageView albumArt = findViewById(R.id.album_art);
        albumArt.setImageResource(trackList.get(currentTrackIndex).getAlbumArtId());

        handler.postDelayed(updateSeekBar, 1000);
    }



    private String formatTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
