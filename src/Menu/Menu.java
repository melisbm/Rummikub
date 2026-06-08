package Menu;

import Console.Console;
import Game.GameManager;
import Rummikub.GameMode;

public class Menu {

    private Console console;
    private GameManager gameManager;

    public Menu(){
        console = new Console();
        gameManager = new GameManager(console);
    }

    public void mainMenu(){

        boolean exit = false;

        while (!exit){

            console.println("\n=== Main Menu ===");
            console.println("1. New game");
            console.println("2. Resume game");
            console.println("3. Exit");

            int pick = console.inputInt(">");

            switch (pick){
                case 1:
                    newGame();
                    break;
                case 2:
                    gameManager.resumeGame();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    console.println("Invalid option.");
            }
        }

        console.close();
    }

    private void newGame(){

        showGameModesMenu();
        GameMode gameMode = askGameMode();

        int numberOfPlayers = askHowManyPlayers(gameMode);
        String[] playersName = askPlayersNames(numberOfPlayers);

        gameManager.newGame(gameMode, playersName);
    }

    private void showGameModesMenu(){
        console.println("1. Rummikub classic");
        console.println("2. Rummy");
        console.println("3. Gin Rummy");
        console.println("4. Argentinian Rummy");
    }

    private GameMode askGameMode(){

        int pick = console.inputInt(">");

        while (pick < 1 || pick > 4){
            pick = console.inputInt("Invalid input, type a number between 1 and 4: ");
        }

        return switch (pick){
            case 1 -> GameMode.CLASSIC;
            case 2 -> GameMode.RUMMY;
            case 3 -> GameMode.GIN;
            case 4 -> GameMode.ARGENTINIAN;
            default -> throw new IllegalArgumentException("Game Mode pick must be in the range between 1 and 4.");
        };
    }

    private int askHowManyPlayers(GameMode gameMode){

        int pick = console.inputInt("How many players are going to play: ");

        while (pick < gameMode.getMinPlayers() || pick > gameMode.getMaxPlayers()){
            String message = "Invalid input, type a number between " + gameMode.getMinPlayers() + " and " + gameMode.getMaxPlayers() + ": ";
            pick = console.inputInt(message);
        }

        return pick;
    }

    private String[] askPlayersNames(int numberOfPlayers){

        String[] names = new String[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            names[i] = console.input("Name of player " + (i + 1) + ": ");
        }

        return names;
    }
}