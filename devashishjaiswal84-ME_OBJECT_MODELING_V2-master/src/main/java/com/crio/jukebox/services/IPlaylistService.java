package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public interface IPlaylistService {
    public String create(int id, String playlistName, List<Integer> listOfSongs);
    public String deletePlaylist(int userId, int playlistId);
    public String play(int userId, int playlistId);
    public String addSongToPlaylist(int userId, int playlistId, List<Integer> songIds);
    public String deleteSongFromPlaylist(int userId, int playlistId, List<Integer> songIds);
    public Song playNextSong(Playlist currentPlayingPlaylist);
    public Song playPreferredSong(Playlist currentPlayingPlaylist, int songId);
    public Song playPreviousSong(Playlist currentPlayingPlaylist);
    
}
