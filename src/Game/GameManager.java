package Game;

import Console.Console;
import Rummikub.Game;
import Rummikub.GameMode;
import Rummikub.RummikubClassicGame;

import java.util.List;

public class GameManager {

    private Console console;
    private GameSaver gameSaver;

    public GameManager(Console console){
        this.console = console;
        this.gameSaver = new GameSaver();
    }

    public void newGame(GameMode gameMode, String[] playersName){

        Game game = createGame(gameMode, playersName);

        if (game == null){
            console.println("That game mode is not available yet.");
            return;
        }

        game.setConsole(console);
        game.setSaver(gameSaver);
        game.start();
    }

    private Game createGame(GameMode gameMode, String[] playersName){

        return switch (gameMode){
            case CLASSIC -> new RummikubClassicGame(playersName);
            default -> null; // els altres modes encara no estan implementades
        };
    }

    public void resumeGame(){

        List<String> savedGames = gameSaver.getSavedGames();

        if (savedGames.isEmpty()){
            console.println("There are no saved games.");
            return;
        }

        console.println("Saved games:");

        for (int i = 0; i < savedGames.size(); i++){
            console.println(" " + i + ". " + savedGames.get(i));
        }

        int pick = console.inputInt("Which game do you want to resume: ");

        while (pick < 0 || pick >= savedGames.size()){
            pick = console.inputInt("Invalid input, type a valid number: ");
        }

        Game game = gameSaver.load(savedGames.get(pick));

        if (game == null){

            console.println("Could not load the game.");
            return;
        }

        game.setConsole(console);
        game.setSaver(gameSaver);
        game.resume();
    }
}