package Rummikub.Tile;

import java.io.Serializable;

public class Tile implements Serializable {

    private static final long serialVersionUID = 1L;

    private int number;
    private TileColor color;
    private boolean isJoker;

    public Tile(int number, TileColor color){
        this.number = number;
        this.color = color;
        isJoker = false;
    }

    public Tile(){
        isJoker = true;
    }

    public TileColor getColor() {
        return color;
    }

    public boolean isJoker() {
        return isJoker;
    }

    public int getNumber() {
        if (isJoker){
            throw new UnsupportedOperationException("Cannot get the number from a Joker.");
        }
        return number;
    }
}
