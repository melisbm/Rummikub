package Rummikub;

import Console.Console;
import Player.Player;
import Game.GameSaver;

import java.io.Serializable;

public abstract class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    protected boolean running = false;
    protected transient Console console = new Console();

    protected transient GameSaver saver;

    protected Player[] players;

    public void start(){
        running = true;
        setUpGame();
        gameLoop();
    }

    public void resume(){
        running = true;
        gameLoop();
    }

    public void setConsole(Console console){
        this.console = console;
    }

    public void stop(){
        running = false;
        console.close();
    }

    public void setSaver(GameSaver saver){
        this.saver = saver;
    }

    protected abstract void setUpGame();
    protected abstract void gameLoop();
}