package Rummikub;

import Console.Console;
import Player.RummikubClassicPlayer;

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



            turnManager.nextTurn();
        }
    }

    protected void setUpGame(){

        for(RummikubClassicPlayer player : players){
            player.setInitialHand(bag);
        }
    }
}
