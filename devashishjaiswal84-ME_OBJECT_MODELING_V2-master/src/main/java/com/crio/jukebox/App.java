package com.crio.jukebox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.codingame.exceptions.NoSuchCommandException;
import com.crio.jukebox.appConfig.ApplicationConfig;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.repositories.SongRepository;



public class App {
    // To run the application  ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
    // To test with sample the application  ./gradlew run --args="INPUT_FILE=jukebox-sample-test.txt"
	public static void main(String[] args) {
       // args[0] = "INPUT_FILE=jukebox-input.txt";
        // for(String s : args)
        //     System.out.println(s);
        
        // ApplicationConfig applicationConfig = new ApplicationConfig();
        // CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        // commandInvoker.executeCommand("LOAD-DATA", Arrays.asList("LOAD-DATA","songs.csv"));
        // commandInvoker.executeCommand("CREATE-USER", Arrays.asList("CREATE-USER", "Raj"));
        // // commandInvoker.executeCommand("CREATE-USER", Arrays.asList("CREATE-USER", "Udit"));
        // // commandInvoker.executeCommand("CREATE-USER", Arrays.asList("CREATE-USER", "Kiran"));
        // commandInvoker.executeCommand("CREATE-PLAYLIST", Arrays.asList("CREATE-PLAYLIST", "1","MY_PLAYLIST_1", "1" ,"2", "3", "4", "5"));
        // commandInvoker.executeCommand("CREATE-PLAYLIST", Arrays.asList("CREATE-PLAYLIST", "1","MY_PLAYLIST_2" ,"2", "3", "4", "5"));
        // commandInvoker.executeCommand("DELETE-PLAYLIST", Arrays.asList("DELETE-PLAYLIST", "1", "2"));
        // commandInvoker.executeCommand("PLAY-PLAYLIST", Arrays.asList("PLAY-PLAYLIST", "1", "1"));
        // commandInvoker.executeCommand("MODIFY-PLAYLIST", Arrays.asList("MODIFY-PLAYLIST", "ADD-SONG", "1","1","7"));
        // commandInvoker.executeCommand("PLAY-SONG", Arrays.asList("PLAY-SONG", "1", "NEXT"));
        

		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        String expectedSequence = "INPUT_FILE";
        String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
        if(expectedSequence.equals(actualSequence)){
            run(commandLineArgs);
        }
	}

    public static void run(List<String> commandLineArgs) {
    // Complete the final logic to run the complete program.
    ApplicationConfig applicationConfig = new ApplicationConfig();
    CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
    BufferedReader reader;
    String inputFile = commandLineArgs.get(0).split("=")[1];
    commandLineArgs.remove(0);
    try {
        reader = new BufferedReader(new FileReader(inputFile));
        String line = reader.readLine();
        while (line != null) {
           // System.out.println(line);
            List<String> tokens = Arrays.asList(line.split(" "));
            commandInvoker.executeCommand(tokens.get(0),tokens);
            // read next line
            line = reader.readLine();
        }
        reader.close();
    } catch (IOException | NoSuchCommandException e) {
        e.printStackTrace();
    }
    }
}
