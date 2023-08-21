package com.crio.jukebox.commands;

import java.util.List;
import javax.swing.Icon;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.PlaylistService;

public class PlaySongCommand implements ICommand {

    IPlaylistService playlistService;
    IPlaylistRepository playlistRepository;

    public PlaySongCommand(IPlaylistService playlistService,
            IPlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        int userId = Integer.parseInt(tokens.get(1));
        String operation = tokens.get(2);
        Song song;
        if (operation.equals("NEXT")) {

            song = playlistService
                    .playNextSong(playlistRepository.getCurrentPlayingPlaylist(userId));
        } else if (operation.equals("BACK")) {
            song = playlistService
                    .playPreviousSong(playlistRepository.getCurrentPlayingPlaylist(userId));
        } else {
            int songId = Integer.parseInt(tokens.get(2));
            song = playlistService.playPreferredSong(
                    playlistRepository.getCurrentPlayingPlaylist(userId), songId);
            if (song == null) {
                System.out.println("Given song id is not a part of the active playlist");
                return;
            }
        }

        String fArtists = "";

        for (int i = 0; i < song.getFeaturedArtist().size(); i++) {
            if (i == 0) {
                fArtists = song.getFeaturedArtist().get(i);
            } else {
                fArtists = fArtists + "," + song.getFeaturedArtist().get(i);
            }

        }

        String output = "Current Song Playing\n" + "Song - " + song.getSongName() + "\n"
                + "Album - " + song.getAlbumName() + "\n" + "Artists - " + fArtists;
        System.out.println(output);

    }

}
