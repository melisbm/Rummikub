package Rummikub;

import Console.Console;
import Player.RummikubClassicPlayer;
import Rummikub.Tile.Tile;
import Rummikub.Tile.TileColor;

import java.util.ArrayList;
import java.util.List;

public class RummikubClassicGame extends Game{

    private Bag bag = new Bag();
    private Board board = new Board();

    private TurnManager turnManager;

    private RummikubClassicPlayer[] players;

    public RummikubClassicGame(String[] playersName, Console console){

        super(console);

        players = new RummikubClassicPlayer[playersName.length];

        for (int i = 0; i < playersName.length; i++) {
            players[i] = new RummikubClassicPlayer(playersName[i]);
        }

        turnManager = new TurnManager(playersName.length);

    }

    protected void gameLoop(){

        while (running){

            RummikubClassicPlayer currPlayer = players[turnManager.getCurrentPlayerIndex()];

            console.println("\nTurn: " + currPlayer.getName() + "\n");
            console.println(board.toString());
            console.println(currPlayer.handToString());

            int optionPick = showOptionsMenu();

            switch (optionPick){
                case 1:
                    if( !bag.getTiles().isEmpty() ){
                        currPlayer.takeRandomTile(bag);
                        turnManager.nextTurn();
                    } else {
                        console.println("There are no more tiles in the bag.");
                    }
                    break;

                case 2:

                    List<Tile> newSet = askPlayerForNewSet(currPlayer);

                    if (isValidSet(newSet)){
                        board.addNewPlay(new Play(newSet));
                        currPlayer.removeTiles(newSet);
                    }
                    else {
                        console.println("Invalid set. Try again.");
                    }

                    break;

                case 3:
                    console.println(board.toString());
                    int setIndex = console.inputInt("Which set? (0-" + (board.getPlays().size()-1) + "): ");
                    break;

                case 4:
                    turnManager.nextTurn();
                    break;

                default:
                    console.println("Invalid option.");
            }
        }
    }

    private int showOptionsMenu(){
        console.println("\nOptions:\n" +
                "1. Draw tile from bag\n" +
                "2. Add new set to board\n" +
                "3. Modify existing set\n" +
                "4. End turn");
        return console.inputInt(">");
    }

    protected void setUpGame(){

        for(RummikubClassicPlayer player : players){
            player.setInitialHand(bag);
        }
    }

    private List<Tile> askPlayerForNewSet(RummikubClassicPlayer player){

        List<Tile> hand = player.getTilesOnHand();

        printHandWithIndices(hand);

        console.println("Enter tile indices separated by spaces: ");
        String input = console.input("");

        String[] indices = input.trim().split(" ");

        List<Tile> selectedTiles = new ArrayList<>();

        for (String indexStr : indices){
            int index = Integer.parseInt(indexStr);
            Tile selectedTile = hand.get(index);
            selectedTiles.add(selectedTile);
        }

        return selectedTiles;
    }

    private void printHandWithIndices(List<Tile> hand){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hand.size(); i++){

            Tile tile = hand.get(i);

            String tileString = "[JK]";

            if (!tile.isJoker()){

                String tileTemplate = "[%d|%s]"; //[tile number|tile color]
                tileString = String.format(tileTemplate, tile.getNumber(), tile.getColor());
            }

            sb.append(i).append(":").append(tileString);

            if (i < hand.size() - 1) sb.append(", ");
        }

        console.println(sb.toString());
    }

    private boolean isValidSet(List<Tile> set){

        if (set.size() < 3) return false;

        return isGroup(set);
    }

    private boolean isGroup(List<Tile> set){

        if (set.size() < 3 || set.size() > 4) return false;

        int groupNumber = -1;
        List<TileColor> usedColors = new ArrayList<>();

        for (Tile tile : set){

            if (tile.isJoker()) continue;

            int tileNumber = tile.getNumber();
            TileColor tileColor = tile.getColor();

            if (groupNumber == -1) groupNumber = tileNumber;
            else if (tileNumber != groupNumber) return false;

            if (usedColors.contains(tileColor)) return false;
            usedColors.add(tileColor);
        }

        return true;
    }
}
