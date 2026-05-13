package Rummikub;

import java.util.*;

public class Bag {

    private List<Tile> numberTiles = new ArrayList<>();
    private List<Tile> jokerTiles = new ArrayList<>();

    public Bag(){

        for(TileColor tileColor : TileColor.values()){

            for(int i = 1; i <= TileType.NUMBER_CAP; i++){
                numberTiles.add(new Tile(i, tileColor));
            }
        }

        for(int i = 1; i <= TileType.JOKERS; i++){
            jokerTiles.add(new Tile());
        }
    }
}
