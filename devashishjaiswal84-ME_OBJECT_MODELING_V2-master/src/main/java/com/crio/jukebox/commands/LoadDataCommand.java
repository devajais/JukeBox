package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.ILoadDataService;

public class LoadDataCommand implements ICommand{
    
    private ILoadDataService loadDataService;

    public LoadDataCommand(ILoadDataService loadDataService){
        this.loadDataService = loadDataService;
    }

    public void execute(List<String> tokens){
        loadDataService.loadData(tokens.get(1));
    }
}
