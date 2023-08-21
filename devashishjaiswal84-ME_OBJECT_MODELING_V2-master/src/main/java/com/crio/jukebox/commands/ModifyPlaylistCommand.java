package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.services.IPlaylistService;

public class ModifyPlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        String operation = tokens.get(1);
        int userId = Integer.parseInt(tokens.get(2));
        int playlistId = Integer.parseInt(tokens.get(3));
        List<Integer> songIds = new ArrayList<>();
        for(int i=4;i<tokens.size();i++){
            songIds.add(Integer.parseInt(tokens.get(i)));
        }

        if(operation.equals("ADD-SONG")){
            System.out.println(playlistService.addSongToPlaylist(userId,playlistId,songIds));
        }
        if(operation.equals("DELETE-SONG")){
            System.out.println(playlistService.deleteSongFromPlaylist(userId,playlistId,songIds));   
        }
    }
    
}
