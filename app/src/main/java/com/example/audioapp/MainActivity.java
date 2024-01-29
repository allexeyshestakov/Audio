package com.example.audioapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int[] songResources = {R.raw.song, R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4};
    private int currentSongIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listViewSongs = findViewById(R.id.listViewSongs);
        final Button btnPlay = findViewById(R.id.btnPlay);
        final Button btnPause = findViewById(R.id.btnPause);
        final Button btnStop = findViewById(R.id.btnStop);

        // Пример списка песен
        final String[] songs = {"Song 1", "Song 2", "Song 3", "Song 4", "Song 5"};

        // Создаем ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);

        // Устанавливаем адаптер для ListView
        listViewSongs.setAdapter(adapter);

        // Обработчик кликов по элементам списка
        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSongIndex = position;
                playSong();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSong();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start(); // Возобновление воспроизведения
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSong();
            }
        });
    }

    private void playSong() {
        if (currentSongIndex != -1) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(MainActivity.this, songResources[currentSongIndex]);
            mediaPlayer.start();
        }
    }

    private void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            currentSongIndex = -1;
        }
    }

    @Override
    protected void onDestroy() {
        stopSong(); // Остановить воспроизведение при уничтожении активности
        super.onDestroy();
    }
}