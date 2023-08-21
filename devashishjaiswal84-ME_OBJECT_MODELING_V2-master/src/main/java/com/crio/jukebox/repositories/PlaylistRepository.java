package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.crio.jukebox.entities.CircularDLL;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public class PlaylistRepository implements IPlaylistRepository{

    private final Map<Integer, List<Playlist>> userPlaylists;
    private final Map<Integer, Integer> userCurrentPlayingMap;
    //private final Map<Integer, Playlist> playlistMap;
  //  private Integer autoIncrement = 0;
    private ISongRepository songRepository;
   // private Playlist currentPlayingPlaylist;
    private final Map<Integer, Integer> userAutoIncrements; // Keep track of auto-increments for each user


    public PlaylistRepository(ISongRepository songRepository) {
        userPlaylists = new HashMap<>();
        userAutoIncrements = new HashMap<>();
        userCurrentPlayingMap = new HashMap<>();
        this.songRepository = songRepository;
    }

    // public PlaylistRepository(Map<Integer, Playlist> userMap) {
    //     this.playlistMap = userMap;
    //     this.autoIncrement = userMap.size();
    // }

    public Playlist ifPlaylistExist(int userId, int playlistId){
        List<Playlist> playlists = userPlaylists.get(userId);
        for(Playlist playlist : playlists){
            if(playlist.getId() == playlistId){
                return playlist;
            }
        }
        return null;
    }


    @Override
    public Playlist save(Playlist playlist) {
        // TODO Auto-generated method stub
        for(int i : playlist.getSongIds()){
            if(songRepository.getSongById(i)==null){
                return null;
            }
        }
        if (playlist.getId() == 0) {
        int userAutoIncrement = userAutoIncrements.getOrDefault(playlist.getUserId(), 1); // Get the user's auto-increment
            Playlist p = new Playlist(userAutoIncrement, playlist.getUserId(), playlist.getPlaylistName(), playlist.getSongIds(), songRepository);
            userPlaylists.computeIfAbsent( playlist.getUserId(), k -> new ArrayList<>()).add(p);
           // autoIncrement++;
           userAutoIncrements.put(playlist.getUserId(), userAutoIncrement + 1); // Increment the user's auto-increment
        
            return p;
         }
        else{
            List<Playlist> playlists = userPlaylists.get(playlist.getUserId());
            if (playlists != null) {
                for (int i = 0; i < playlists.size(); i++) {
                    if (playlists.get(i).getId() == playlist.getId()) {
                        playlists.set(i, playlist); // Update the existing playlist
                        break;
                    }
                }
            }
            return playlist;
        }
    }

    public Playlist getPlaylist(int userId, int playlistId){
        List<Playlist> playlists = userPlaylists.get(userId);
        for(Playlist playlist : playlists){
            if(playlist.getId() == playlistId){
                return playlist;
            }
        }
        return null;
    }

    public Playlist deletePlaylist(int userId, int playlistId){
        List<Playlist> playlists = userPlaylists.get(userId);
        if (playlists != null) {
            for (Playlist playlist : playlists) {
                if (playlist.getId() == playlistId) {
                    playlists.remove(playlist);
                    return playlist;
                }
            }
        }
        return null; // Playlist not found
    }

    @Override
    public Song playPlaylist(int userId, int playlistId){
        List<Playlist> playlists = userPlaylists.get(userId);
        if (playlists != null) {
            for (Playlist playlist : playlists) {
                if (playlist.getId() == playlistId) {
                    CircularDLL songInPlaylist = playlist.getSongsinCDLL(playlist.getSongIds());
                    if (songInPlaylist != null && songInPlaylist.getHead() != null) {
                        userCurrentPlayingMap.put(userId,playlistId);
                        return songInPlaylist.getHead().getSong();
                    }
                }
            }
        }
        return null; // Playlist not found or empty
    }

    public Playlist getCurrentPlayingPlaylist(int userId){
        int playlistId = userCurrentPlayingMap.getOrDefault(userId, -1);
        if(playlistId!=-1){
            return getPlaylist(userId, playlistId);
        }
        return null;
    }
}