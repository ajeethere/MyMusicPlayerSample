package com.example.mymusicplayersample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
ImageView previous,next,pausePlay;
MediaPlayer mediaPlayer;
SeekBar seekBar;
boolean playing=false;
int position;
Runnable runnable;
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        pausePlay=findViewById(R.id.pause_play);
        seekBar=findViewById(R.id.seekbar);

        handler=new Handler();



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
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

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playing){
                    if (mediaPlayer==null){
                        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.all_in_my_head);
                        mediaPlayer.start();
                    }else if (!mediaPlayer.isPlaying()){
                        mediaPlayer.seekTo(position);
                        mediaPlayer.start();
                    }

                    pausePlay.setImageDrawable(getResources().getDrawable(R.drawable.pause_play));
                    playing=true;
                }else {
                    mediaPlayer.pause();
                    pausePlay.setImageDrawable(getResources().getDrawable(R.drawable.play_pause));
                    position=mediaPlayer.getCurrentPosition();
                    playing=false;
                }
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
    }




    public void playCircle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()){
            runnable=new Runnable() {
                @Override
                public void run() {
                    playCircle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }
}
