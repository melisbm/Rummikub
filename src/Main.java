import Console.Console;
import Menu.Menu;
import Rummikub.Game;
import Rummikub.RummikubClassicGame;
public class Main {
    public static void main(String[] args){

        Console console = new Console();

        Menu menu = new Menu(console);
        menu.mainMenu();

        Game game = switch(menu.getGameModePick()){
            case CLASSIC -> new RummikubClassicGame(menu.getPlayersName(), console);
            case RUMMY -> new RummikubClassicGame(menu.getPlayersName(), console);
            case GIN -> new RummikubClassicGame(menu.getPlayersName(), console);
            case ARGENTINIAN -> new RummikubClassicGame(menu.getPlayersName(), console);
        };

        game.start();
    }
}