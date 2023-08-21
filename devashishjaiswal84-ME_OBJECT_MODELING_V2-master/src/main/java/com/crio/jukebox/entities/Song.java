package com.crio.jukebox.entities;

import java.util.List;

//package com.mkyong.io.csv.opencsv;


public class Song extends BaseEntity{
    
    
    private String songName;
    private String genre;
    private String albumName;
    private String albumArtist;
    private List<String> featuredArtist;

    public Song(int id, String songName, String genre, String albumName, String albumArtist, List<String> featuredArtist){
        this.id = id;
        this.songName = songName;
        this.genre = genre;
        this.albumName = albumName;
        this.albumArtist = albumArtist;
        this.featuredArtist = featuredArtist;
    }

    public String getSongName(){
        return songName;
    }
    public String getGenre(){
        return genre;
    }
    public String getAlbumName(){
        return albumName;
    }
    public String getAlbumArtist(){
        return albumArtist;
    }
    public List<String> getFeaturedArtist(){
        return featuredArtist;
    }


}
