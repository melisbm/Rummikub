package Menu;

import Console.Console;
import Rummikub.GameMode;

public class Menu {

    Console console;

    GameMode gameMode;
    String[] playersName;

    public Menu(Console console) {
        this.console = console;
    }

    public void mainMenu() {

        console.println("=== Main Menu ===");

        showGameModesMenu();
        gameMode = askGameMode();

        int playerCount = askHowManyPlayers(gameMode);

        askPlayersName(playerCount);
    }

    private void showGameModesMenu() {

        console.println("1. Rummikub classic");
        console.println("2. Rummy");
        console.println("3. Gin Rummy");
        console.println("4. Argentinian Rummy");
    }

    private GameMode askGameMode() {

        int pick = console.inputInt(">");

        while (pick < 1 || pick > GameMode.values().length) {

            String pickOutOfRangeMessage = "Invalid input, type a number between 1 and " + GameMode.values().length + ": ";
            pick = console.inputInt(pickOutOfRangeMessage);
        }

        return switch (pick) {
            case 1 -> GameMode.CLASSIC;
            case 2 -> GameMode.RUMMY;
            case 3 -> GameMode.GIN;
            case 4 -> GameMode.ARGENTINIAN;
            default -> throw new IllegalArgumentException("Game Mode pick must be in the range between 1 and " + GameMode.values().length + ".");
        };
    }

    private int askHowManyPlayers(GameMode gameMode) {

        int pick = console.inputInt("How many players are going to play: ");

        while (pick < gameMode.getMinPlayers() || pick > gameMode.getMaxPlayers()) {

            String pickOutOfRangeMessage = "Invalid input, type a number between 1 and " + gameMode.getMaxPlayers() + ": ";
            pick = console.inputInt(pickOutOfRangeMessage);
        }

        return pick;
    }

    private void askPlayersName(int playerCount) {

        playersName = new String[playerCount];

        for (int i = 0; i < playerCount; i++) {
            playersName[i] = console.input("Who is player " + (i + 1) + ": ");
        }
    }

    public GameMode getGameModePick() {
        return gameMode;
    }

    public String[] getPlayersName() {
        return playersName;
    }
}