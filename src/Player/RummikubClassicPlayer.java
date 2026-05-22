package Player;

import Rummikub.Bag;
import Rummikub.Tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class RummikubClassicPlayer extends Player{

    private List<Tile> tilesOnHand = new ArrayList<>();

    public RummikubClassicPlayer(String name){
        this.name = name;
    }

    public void takeRandomTile(Bag bag){

        int randomIndex = (int) ( Math.random() * bag.getTiles().size() );
        Tile randomTile = bag.getTile(randomIndex);

        tilesOnHand.add(randomTile);
        bag.removeTile(randomTile);
    }

    public void setInitialHand(Bag bag){

        for(int i = 0; i < 14; i++){
            takeRandomTile(bag);
        }
    }

    public List<Tile> setTilesOnHand(){
        return tilesOnHand;
    }
}
