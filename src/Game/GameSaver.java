package Game;

import Rummikub.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameSaver {

    private static final String SAVES_FOLDER = "saves";
    private static final String FILE_EXTENSION = ".dat";

    public GameSaver(){

        File folder = new File(SAVES_FOLDER);

        if ( !folder.exists() ){
            folder.mkdir();
        }
    }

    public void save(Game game, String saveName){

        String path = SAVES_FOLDER + File.separator + saveName + FILE_EXTENSION;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(game);
        } catch (IOException e){
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public Game load(String saveName){

        String path = SAVES_FOLDER + File.separator + saveName + FILE_EXTENSION;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            return (Game) in.readObject();

        } catch (IOException | ClassNotFoundException e){

            System.out.println("Error loading the game: " + e.getMessage());
            return null;
        }
    }

    public List<String> getSavedGames(){

        List<String> savedGames = new ArrayList<>();

        File folder = new File(SAVES_FOLDER);
        File[] files = folder.listFiles();

        if (files != null){

            for (File file : files){

                String fileName = file.getName();

                if (fileName.endsWith(FILE_EXTENSION)){
                    String name = fileName.substring(0, fileName.length() - FILE_EXTENSION.length());
                    savedGames.add(name);
                }
            }
        }

        return savedGames;
    }
}