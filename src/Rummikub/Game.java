package Rummikub;

import Console.Console;
import Player.Player;

public abstract class Game {

    protected boolean running = false;
    protected Console console;

    protected Player[] players;

    public Game(Console console){
        this.console = console;
    }

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
