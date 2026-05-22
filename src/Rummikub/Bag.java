package Rummikub;

import Rummikub.Tile.Tile;
import Rummikub.Tile.TileColor;
import Rummikub.Tile.TileType;

import java.util.*;

public class Bag {

    private List<Tile> tiles = new ArrayList<>();

    public Bag(){

        for(TileColor tileColor : TileColor.values()){
            for(int i = 0; i < 2; i++) {
                for (int j = 1; j <= TileType.NUMBER_CAP; j++) {
                    tiles.add(new Tile(j, tileColor));
                }
            }
        }

        for(int i = 1; i <= TileType.JOKERS; i++){
            tiles.add(new Tile());
        }
    }
}
