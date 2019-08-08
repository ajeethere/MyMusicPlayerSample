package com.example.mymusicplayersample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongsViewHolder> {
    public static List<SongListObjects> data;
    Context context;
    public static int songPosition=0;

    public SongsListAdapter(List<SongListObjects> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_items,parent,false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, final int position) {
        final SongListObjects songListObjects=data.get(position);
        holder.bind(songListObjects);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MainActivity.class);
                songPosition=position;
                i.putExtra("SONG",songListObjects.getSongData());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
