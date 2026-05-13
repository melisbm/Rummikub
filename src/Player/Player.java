package Player;

import Rummikub.Tile;

public class Player {

    private String name;
    private Tile[] tilesOnHand;
    private int points = 0;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void takeRandomTile(){

    }
}
