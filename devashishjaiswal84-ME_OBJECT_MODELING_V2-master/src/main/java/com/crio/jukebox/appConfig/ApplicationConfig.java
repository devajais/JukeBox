package com.crio.jukebox.appConfig;

import com.crio.jukebox.commands.ICommand;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.PlaylistRepository;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreatePlaylistCommand;
import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlaylistCommand;
import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlaylistCommand;
import com.crio.jukebox.commands.PlayPlaylistCommand;
import com.crio.jukebox.commands.PlaySongCommand;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.services.ILoadDataService;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.LoadDataService;
import com.crio.jukebox.services.PlaylistService;
import com.crio.jukebox.services.UserService;

public class ApplicationConfig {

    private final ISongRepository songRepository = new SongRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final IPlaylistRepository playlistRepository = new PlaylistRepository(songRepository);


    private final ILoadDataService loadDataService = new LoadDataService(songRepository);
    private final IUserService userService = new UserService(userRepository);
    private final IPlaylistService playlistService = new PlaylistService(playlistRepository,songRepository);


    private final ICommand loadDataCommand = new LoadDataCommand(loadDataService);
    private final ICommand createUserCommand = new CreateUserCommand(userService);
    private final ICommand createPlaylistCommand = new CreatePlaylistCommand(playlistService);
    private final ICommand deletePlaylistCommand = new DeletePlaylistCommand(playlistService);
    private final ICommand playPlaylistCommand = new PlayPlaylistCommand(playlistService);
    private final ICommand modifyPlaylistCommand = new ModifyPlaylistCommand(playlistService);
    private final ICommand playSongCommand = new PlaySongCommand(playlistService,playlistRepository);
    

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("LOAD-DATA",loadDataCommand);
        commandInvoker.register("CREATE-USER",createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST", createPlaylistCommand);
        commandInvoker.register("DELETE-PLAYLIST", deletePlaylistCommand);
        commandInvoker.register("PLAY-PLAYLIST", playPlaylistCommand);
        commandInvoker.register("MODIFY-PLAYLIST", modifyPlaylistCommand);
        commandInvoker.register("PLAY-SONG", playSongCommand);
        return commandInvoker;
    }
}
