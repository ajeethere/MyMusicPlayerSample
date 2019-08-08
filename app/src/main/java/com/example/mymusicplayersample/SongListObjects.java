package com.example.mymusicplayersample;

public class SongListObjects {
    String songName,singerName,songData;

    public SongListObjects(String songName, String singerName, String songData) {
        this.songName = songName;
        this.singerName = singerName;
        this.songData = songData;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }
}
