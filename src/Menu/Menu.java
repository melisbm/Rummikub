package Menu;

import Console.Console;
import Rummikub.GameMode;

public class Menu {

    Console console;

    public Menu() {
        console = new Console();
    }

    public void mainMenu() {

        console.println("=== Main Menu ===");

        showGameModesMenu();
        GameMode gameModePick = askGameMode();

        int numberOfPlayers = askHowManyPlayers(gameModePick);

        console.close();
    }

    private void showGameModesMenu() {

        console.println("1. Rummikub classic");
        console.println("2. Rummy");
        console.println("3. Gin Rummy");
        console.println("4. Argentinian Rummy");

    }

    private GameMode askGameMode() {

        int pick = console.inputInt(">");

        while (pick < 1 || pick > 4) {

            String pickOutOfRangeMessage = "Invalid input, type a number between 1 and 4: ";
            pick = console.inputInt(pickOutOfRangeMessage);
        }

        return switch (pick) {
            case 1 -> GameMode.CLASSIC;
            case 2 -> GameMode.RUMMY;
            case 3 -> GameMode.GIN;
            case 4 -> GameMode.ARGENTINIAN;
            default -> throw new IllegalArgumentException("Game Mode pick must be in the range between 1 and 4.");
        };
    }

    private int askHowManyPlayers(GameMode gameMode) {

        int pick = console.inputInt("How many players are going to play: ");

        while (pick < gameMode.getMinPlayers() || pick > gameMode.getMaxPlayers()) {

            String pickOutOfRangeMessage = "Invalid input, type a number between 1 and 4: ";
            pick = console.inputInt(pickOutOfRangeMessage);
        }

        return pick;
    }
}