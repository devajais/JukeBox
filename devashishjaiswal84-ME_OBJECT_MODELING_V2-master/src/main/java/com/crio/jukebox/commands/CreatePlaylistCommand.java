package com.crio.jukebox.commands;

import java.util.*;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.PlaylistService;

public class CreatePlaylistCommand implements ICommand{

    private IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        int userId = Integer.parseInt(tokens.get(1));
        String playlistName = tokens.get(2);
        List<Integer> listOfSongs = new ArrayList<>();
        for(int i=3;i<tokens.size();i++){
            listOfSongs.add(Integer.parseInt(tokens.get(i)));
        }
        System.out.println(playlistService.create(userId,playlistName,listOfSongs));
    }

    
    
}
