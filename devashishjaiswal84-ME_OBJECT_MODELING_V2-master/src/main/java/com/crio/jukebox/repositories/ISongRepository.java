package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.*;

public interface ISongRepository {
    public void addSong(Song song);
    public void removeSong(int id);
    public Song getSongById(int id);
    public void loadSongsFromCSV(String filename);
}
