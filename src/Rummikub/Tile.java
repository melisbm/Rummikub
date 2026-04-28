package Rummikub;

public class Tile {

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
        return number;
    }
}
