package com.crio.jukebox.services;

import com.crio.jukebox.repositories.ISongRepository;

public class LoadDataService implements ILoadDataService{
    
    private ISongRepository songRepository;

    public LoadDataService(ISongRepository songRepository){
        this.songRepository = songRepository;
    }

    public void loadData(String filename){
        songRepository.loadSongsFromCSV(filename);
    }
    
}
