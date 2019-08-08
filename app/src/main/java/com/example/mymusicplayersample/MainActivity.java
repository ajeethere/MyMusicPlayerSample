package com.example.mymusicplayersample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    ImageView previous, next, pausePlay, songsList;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    boolean playing = true;
    int position,listPosition;
    Runnable runnable;
    Uri song;
    Handler handler;
    String songString;

    NotificationCompat.Builder notification;

    String CHANNEL_ID="musicPlayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        pausePlay = findViewById(R.id.pause_play);
        seekBar = findViewById(R.id.seekbar);
        songsList = findViewById(R.id.songs_list);
        showNotification();

        handler = new Handler();
        Intent intent = getIntent();
        songString = intent.getStringExtra("SONG");
        listPosition=SongsListAdapter.songPosition;

        song = Uri.parse(songString);

        if (mediaPlayer!=null) {
            mediaPlayer.stop();
        }

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song);
            mediaPlayer.start();
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mediaPlayer.getDuration());
                playCircle();
                mediaPlayer.start();
            }
        });

        songsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                onBackPressed();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listPosition < 1) {
                    listPosition=SongsListAdapter.data.size()-1;
                } else {
                    listPosition--;
                }
                songString = SongsListAdapter.data.get(listPosition).getSongData();
                song=Uri.parse(songString);
                mediaPlayer.stop();

                mediaPlayer = MediaPlayer.create(getApplicationContext(),song);
                mediaPlayer.seekTo(position);
                mediaPlayer.start();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        seekBar.setMax(mediaPlayer.getDuration());
                        playCircle();
                        mediaPlayer.start();
                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listPosition ==SongsListAdapter.data.size()-1) {
                    listPosition=0;
                } else {
                    listPosition++;
                }
                songString = SongsListAdapter.data.get(listPosition).getSongData();
                song=Uri.parse(songString);
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getApplicationContext(),song);
                mediaPlayer.seekTo(position);
                mediaPlayer.start();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        seekBar.setMax(mediaPlayer.getDuration());
                        playCircle();
                        mediaPlayer.start();
                    }
                });
            }
        });
        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playing) {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), song);
                        mediaPlayer.start();
                    } else if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(position);
                        mediaPlayer.start();
                    }

                    pausePlay.setImageDrawable(getResources().getDrawable(R.drawable.pause_play));
                    playing = true;
                } else {
                    mediaPlayer.pause();
                    pausePlay.setImageDrawable(getResources().getDrawable(R.drawable.play_pause));
                    position = mediaPlayer.getCurrentPosition();
                    playing = false;
                }
            }
        });
    }


    public void playCircle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCircle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }
    public void showNotification(){
        RemoteViews notificationLayout = new RemoteViews(this.getPackageName(), R.layout.notification_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Music Player").setSmallIcon(R.drawable.play)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setAutoCancel(false);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, notification.build());
    }
}
