package com.example.mymusicplayersample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class SongsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        recyclerView=findViewById(R.id.songs_list_recycler_view);
        ContentResolver cr = this.getContentResolver();

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        List<SongListObjects> songs=new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String singer=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String pic=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.BOOKMARK));
                    songs.add(new SongListObjects(name,singer,data));
                    // Add code to get more column here

                    // Save to your list here
                }
                recyclerView.setAdapter(new SongsListAdapter(songs,this));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

            }
        }

        cur.close();
    }
}
