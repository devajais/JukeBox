package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public interface IPlaylistRepository {
    public Playlist save(Playlist playlist);
    public Playlist deletePlaylist(int userId, int playlistId);
    public Song playPlaylist(int userId, int playlistId);
    public Playlist getPlaylist(int userId, int playlistId);
    public Playlist getCurrentPlayingPlaylist(int userId);
    public Playlist ifPlaylistExist(int userId, int playlistId);
    
}
