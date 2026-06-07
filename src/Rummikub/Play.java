package Rummikub;

import Rummikub.Tile.Tile;
import java.util.ArrayList;
import java.util.List;

public class Play {

    private List<Tile> tiles = new ArrayList<>();

    public Play() { }

    public Play(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int size() {
        return tiles.size();
    }
}