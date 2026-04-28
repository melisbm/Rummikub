package Menu;

import Console.Console;
import Rummikub.GameModes;

public class Menu {

    Console console;

    public Menu(){
        console = new Console();
    }

    public void mainMenu(){
        showMainMenu();
        GameModes gameModePick = askGameMode();
        console.close();
    }

    public void showMainMenu(){
        console.println("=== Main Menu ===");
        showGameModesMenu();
    }

    public void showGameModesMenu(){

        console.println("1. Rummikub classic");
        console.println("2. Rummy");
        console.println("3. Gin Rummy");
        console.println("4. Argentinian Rummikub");

    }

    public GameModes askGameMode(){

        int pick = console.inputInt(">");

        while (pick < 1 || pick > 4){

            String pickOutOfRangeMessage = "Invalid input, type a number between 1 and 4: ";
            pick = console.inputInt(pickOutOfRangeMessage);
        }

        return switch (pick){
            case 1 -> GameModes.CLASSIC;
            case 2 -> GameModes.RUMMY;
            case 3 -> GameModes.GIN;
            case 4 -> GameModes.ARGENTINIAN;
            default -> throw new IllegalArgumentException("Game Mode pick must be in the range between 1 and 4.");
        };
    }

}
