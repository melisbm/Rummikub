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

    private MoveLog moveLog = new MoveLog();

    public RummikubClassicGame(String[] playersName){

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

                        moveLog.register(currPlayer.getName() + " drew a tile from the bag");

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

                        moveLog.register(currPlayer.getName() + " played a set: " + describeTiles(newSet));
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

                case 5:
                    saveAndQuit();
                    break;

                default:
                    console.println("Invalid option.");
            }

            if (currPlayer.hasWon()){
                console.println("\n=== " + currPlayer.getName() + " has won the game! ===");
                console.println("\n" + moveLog.toString());
                running = false;
            }
        }
    }

    private int showOptionsMenu(){
        console.println("\nOptions:\n" +
                "1. Draw tile from bag\n" +
                "2. Add new set to board\n" +
                "3. Modify existing set\n" +
                "4. End turn\n" +
                "5. Save and quit");
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

        return isGroup(set) || isRun(set);
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

    private boolean isRun(List<Tile> set){

        if (set.size() < 3) return false;

        List<Tile> numberedTiles = new ArrayList<>();
        int jokers = 0;

        for (Tile tile : set){
            if (tile.isJoker()) jokers++;
            else numberedTiles.add(tile);
        }

        if (numberedTiles.isEmpty()) return false;

        TileColor runColor = numberedTiles.get(0).getColor();
        for (Tile tile : numberedTiles){
            if (tile.getColor() != runColor) return false;
        }
        sortByNumber(numberedTiles);

        for (int i = 1; i < numberedTiles.size(); i++){

            int prev = numberedTiles.get(i - 1).getNumber();
            int curr = numberedTiles.get(i).getNumber();

            if (curr == prev) return false;

            int gap = curr - prev - 1;
            jokers -= gap;

            if (jokers < 0) return false;
        }

        return true;
    }

    private void sortByNumber(List<Tile> tiles){

        for (int i = 0; i < tiles.size() - 1; i++){

            int minIndex = i;

            for (int j = i + 1; j < tiles.size(); j++){
                if (tiles.get(j).getNumber() < tiles.get(minIndex).getNumber()){
                    minIndex = j;
                }
            }

            if (minIndex != i){
                Tile temp = tiles.get(i);
                tiles.set(i, tiles.get(minIndex));
                tiles.set(minIndex, temp);
            }
        }
    }

    private String describeTiles(List<Tile> tiles){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tiles.size(); i++){

            Tile tile = tiles.get(i);
            String tileString = tile.isJoker() ? "[JK]" : "[" + tile.getNumber() + "|" + tile.getColor() + "]";

            sb.append(tileString);
            if (i < tiles.size() - 1) sb.append(", ");
        }

        return sb.toString();
    }

    private void saveAndQuit(){

        String saveName = console.input("Save name: ");
        saver.save(this, saveName);

        console.println("Game saved as '" + saveName + "'.");

        running = false;
    }

}
