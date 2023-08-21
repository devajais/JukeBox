package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.entities.CircularDLL.Node;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.SongRepository;

public class Playlist extends BaseEntity {

    private int userId;
    private String playlistName;
    private List<Integer> songIds;
    private CircularDLL songInPlaylist;
    private Song song;
    private IUserRepository userRepository;
    private ISongRepository songRepository;

    public Playlist(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Playlist(int userId, String playlistName, List<Integer> songIds,
            ISongRepository songRepository) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.songIds = songIds;
        this.songInPlaylist = new CircularDLL();
        this.songRepository = songRepository;

        for (int songId : songIds) {
            // checking user is persisted!
            // System.out.println(songRepository.getSongById(2).getSongName());
            Song song = songRepository.getSongById(songId);
            if (song != null) {
                songInPlaylist.addSong(song);
            }
        }
    }

    public Playlist(int id, int userId, String playlistName, List<Integer> songIds,
            ISongRepository songRepository) {
        this.id = id;
        this.userId = userId;
        this.playlistName = playlistName;
        this.songIds = songIds;
        this.songInPlaylist = new CircularDLL();
        this.songRepository = songRepository;
        getSongsinCDLL(songIds);
        // for (int songId : songIds) {
        // Song song = songRepository.getSongById(songId);
        // if (song != null) {
        // songInPlaylist.addSong(song);
        // }
        // }

        // System.out.println(songInPlaylist.playPreviousSong().getSongName());
        // System.out.println(songInPlaylist.playNextSong().getSongName());
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public int getUserId() {
        return userId;
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        Node currentNode = songInPlaylist.getHead();

        if (currentNode == null) {
            return songs;
        }

        do {
            songs.add(currentNode.getSong());
            currentNode = currentNode.getNext();
        } while (currentNode != songInPlaylist.getHead());

        return songs;
    }

    public List<Integer> getSongIds() {
        return songIds;
    }

    public CircularDLL getSongsinCDLL(List<Integer> songIds) {
        for (int songId : songIds) {
            Song song = songRepository.getSongById(songId);
            if (song != null) {
                songInPlaylist.addSong(song);
            }
        }
        return songInPlaylist;
    }




    public Song playPreviousSong() {
        if (songInPlaylist == null) {
            return null; // Playlist is empty
        }

        // Move to the previous song and return it
        songInPlaylist.playPreviousSong();
        return songInPlaylist.getHead().getSong();
    }

    public Song playNextSong() {
        if (songInPlaylist == null) {
            return null; // Playlist is empty
        }

        // Move to the next song and return it
        songInPlaylist.playNextSong();
        return songInPlaylist.getHead().getSong();
    }

    public Song playPreferredSong(int songId) {
        if (songInPlaylist == null) {
            return null; // Playlist is empty
        }

        // Find the song with the specified ID in the playlist
        CircularDLL.Node node = songInPlaylist.findNode(songId);
        if (node != null) {
            songInPlaylist.setCurrentNode(node); // Set the current node to the found song
            return songInPlaylist.getHead().getSong();
        } else {
            return null; // Song not found in the playlist
        }
    }
}
