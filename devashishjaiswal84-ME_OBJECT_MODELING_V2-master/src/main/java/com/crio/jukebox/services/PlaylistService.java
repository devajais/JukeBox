package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;

public class PlaylistService implements IPlaylistService{
    
    private IPlaylistRepository playlistRepository;
    private ISongRepository songRepository;
    public PlaylistService(IPlaylistRepository playlistRepository, ISongRepository songRepository){
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }
   
    

    public String create(int userId, String playlistName, List<Integer> listOfSongs){

       // System.out.println(userRepository.getSize());
        Playlist playlist = new Playlist(userId, playlistName, listOfSongs,songRepository);
        Playlist p = playlistRepository.save(playlist);
        if(p==null){
            return "Some Requested Songs Not Available. Please try again.";
        }
        return "Playlist ID - " + p.getId() ;
    }

    public String deletePlaylist(int userId, int playlistId){
        // if(playlistRepository.ifPlaylistExist(userId, playlistId)==null){
        //     return "Playlist Not Found";
        // }
        Playlist p = playlistRepository.deletePlaylist(userId, playlistId);
        if(p!=null)
            return "Delete Successful";

        return "Playlist Not Found";
        //return p.getPlaylistName() + " - Deleted";
    }

    @Override
    public String play(int userId, int playlistId){

        Song song = playlistRepository.playPlaylist(userId, playlistId);
        if(song == null){
            return "Playlist is empty.";
        }
        String fArtists = "";

        for(int i=0;i<song.getFeaturedArtist().size();i++){
            if(i==0){
                fArtists = song.getFeaturedArtist().get(i);
            }
            else{
                fArtists = fArtists + "," + song.getFeaturedArtist().get(i);
            }
            
        }
        
        return "Current Song Playing\n"+
        "Song - "+ song.getSongName()+"\n"+ 
        "Album - "+ song.getAlbumName() + "\n"+
        "Artists - "+  fArtists;
    }



    @Override
    public String addSongToPlaylist(int userId, int playlistId, List<Integer> songIds) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.getPlaylist(userId, playlistId);
        if (playlist != null) {
            for (int songId : songIds) {
                Song songToAdd = songRepository.getSongById(songId);
                if (songToAdd != null) {
                    playlist.getSongIds().add(songId); // Add each song ID to the playlist's list
                } else {
                    return "Some Requested Songs Not Available. Please try again."; // Song not found in the song repository
                }
            }
            Playlist playlistObj = new Playlist(playlistId,userId,playlist.getPlaylistName(), playlist.getSongIds(),songRepository);
            Playlist p = playlistRepository.save(playlistObj);
            String finalSongIds = "";

        for(int i=0;i<p.getSongIds().size();i++){
            if(i==0){
                finalSongIds = Integer.toString(p.getSongIds().get(i));
            }
            else{
                finalSongIds = finalSongIds + " " + p.getSongIds().get(i);
            }
            
        }
            return "Playlist ID - "+p.getId()+"\n"+
            "Playlist Name - "+p.getPlaylistName()+"\n"+
            "Song IDs - " +finalSongIds;
    }
    return "Some Requested Songs Not Available. Please try again.";
    }


    @Override
    public String deleteSongFromPlaylist(int userId, int playlistId, List<Integer> songIds) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.getPlaylist(userId, playlistId);
        if (playlist != null) {
            for (int songId : songIds) {
                if (playlist.getSongIds().contains(songId)) {
                    Song songToDelete = songRepository.getSongById(songId);
                    if (songToDelete != null) {
                        playlist.getSongIds().remove(Integer.valueOf(songId)); // Remove each song ID from the playlist's list
                    }
                } else {
                    return "Some Requested Songs for Deletion are not present in the playlist. Please try again."; // User or playlist not found
                    // Song not found in the playlist
                }
            }
            Playlist playlistObj = new Playlist(playlistId,userId,playlist.getPlaylistName(), playlist.getSongIds(),songRepository);
            Playlist p = playlistRepository.save(playlistObj);
            String finalSongIds = "";

        for(int i=0;i<p.getSongIds().size();i++){
            if(i==0){
                finalSongIds = Integer.toString(p.getSongIds().get(i));
            }
            else{
                finalSongIds = finalSongIds + " " + p.getSongIds().get(i);
            }
            
        }
            return "Playlist ID - "+p.getId()+"\n"+
            "Playlist Name - "+p.getPlaylistName()+"\n"+
            "Song IDs - " +finalSongIds;
    }
        return "Some Requested Songs for Deletion are not present in the playlist. Please try again."; // User or playlist not found
    }


    public Song playPreviousSong(Playlist currentPlaylist) {
        if (currentPlaylist != null) {
            return currentPlaylist.playPreviousSong();
        }
        return null; // User or playlist not found
    }

    public Song playNextSong(Playlist currentPlaylist) {
        if (currentPlaylist != null) {
            return currentPlaylist.playNextSong();
        }
        return null; // User or playlist not found
    }

    public Song playPreferredSong(Playlist currentPlaylist, int songId) {
        if (currentPlaylist != null) {
            return currentPlaylist.playPreferredSong(songId);
        }
        return null; // User or playlist not found
    }
    

}
