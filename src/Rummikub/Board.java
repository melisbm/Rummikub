package Rummikub;

import Rummikub.Tile.Tile;

import java.util.List;

public class Board {

    private List<List<Tile>> tiles;

    public Board(){

    }

    public void addNewTileSet(List<Tile> tileSet){
        tiles.add(tileSet);
    }

    public void modifyTileSet(int index){
    }
}
