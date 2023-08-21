package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.services.IPlaylistService;

public class DeletePlaylistCommand implements ICommand{

    private IPlaylistService playlistService;

    public DeletePlaylistCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        int userId = Integer.parseInt(tokens.get(1));
        int playlistId = Integer.parseInt(tokens.get(2));

        System.out.println(playlistService.deletePlaylist(userId, playlistId));
        
    }
    
}
