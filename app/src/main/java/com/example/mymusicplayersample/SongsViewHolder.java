package com.example.mymusicplayersample;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongsViewHolder extends RecyclerView.ViewHolder {
    View view;
    public SongsViewHolder(@NonNull View itemView) {
        super(itemView);
        view=itemView;
    }
    public void bind(SongListObjects songListObjects){
        TextView songName=view.findViewById(R.id.song_name);
        songName.setText(songListObjects.getSongName());
        TextView singerName=view.findViewById(R.id.singer_name);
        singerName.setText(songListObjects.getSingerName());
    }
}
