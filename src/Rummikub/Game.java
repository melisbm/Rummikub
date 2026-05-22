package Rummikub;

import Console.Console;
import Player.Player;

public abstract class Game {

    protected boolean running = false;
    protected Console console = new Console();

    protected Player[] players;

    public void start(){
        running = true;
        setUpGame();
        gameLoop();
    }

    public void stop(){
        running = false;
        console.close();
    }

    protected abstract void setUpGame();
    protected abstract void gameLoop();
}
