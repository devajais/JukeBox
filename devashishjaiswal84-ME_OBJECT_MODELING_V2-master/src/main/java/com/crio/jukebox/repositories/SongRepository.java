package com.crio.jukebox.repositories;
import java.io.FileReader;
import java.io.IOException;
import com.crio.jukebox.entities.Song;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepository implements ISongRepository{
    private Map<Integer, Song> songs;

    public SongRepository() {
        songs = new HashMap<>();
    }

    public void addSong(Song song) {
        songs.put(song.getId(), song);
    }

    public void removeSong(int songId) {
        songs.remove(songId);
    }

    public Song getSongById(int songId) {
        return songs.get(songId);
    }


    public void loadSongsFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                int id = Integer.parseInt(line[0]);
                String name = line[1];
                String genre = line[2];
                String album = line[3];
                String albumArtist = line[4];
                String[] featuredArtists = line[5].split("#");
                List<String> featuredArtistsList = new ArrayList<>(Arrays.asList(featuredArtists));
                Song song = new Song(id, name, genre, album, albumArtist, featuredArtistsList);
                addSong(song);
                
            }
            // String st = "";
            // for(String s : songs.get(1).getFeaturedArtist()){
                
            // st = st+" "+s;
            // }
            
            // System.out.println(songs.get(20).getSongName());
            System.out.println("Songs Loaded successfully");
        } catch (IOException | CsvException e) {
            System.out.println("Error loading songs from CSV file: " + e.getMessage());
        }
    }

   
}
