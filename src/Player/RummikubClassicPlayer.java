package Player;

import Rummikub.Bag;
import Rummikub.Tile.Tile;
import Rummikub.Tile.TileColor;

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

    public void removeTiles(List<Tile> tilesToRemove){
        tilesOnHand.removeAll(tilesToRemove);
    }

    public void setInitialHand(Bag bag){

        for(int i = 0; i < 14; i++){
            takeRandomTile(bag);
        }
    }

    public List<Tile> getTilesOnHand(){
        return tilesOnHand;
    }

    public String handToString(){

        StringBuilder stringBuilder = new StringBuilder();

        String tileTemplate = "[%d|%s]"; //[tile number|color]

        for (int i = 0; i < tilesOnHand.size(); i++) {

            String formatedTile;

            Tile tile = tilesOnHand.get(i);

            if (tile.isJoker()) {
                formatedTile = "[JK]";
            }
            else{
                String color = switch (tile.getColor()){
                    case TileColor.BLACK -> "Black";
                    case TileColor.YELLOW -> "Yellow";
                    case TileColor.BLUE -> "Blue";
                    case TileColor.RED -> "Red";
                };

                formatedTile = String.format(tileTemplate, tile.getNumber(), color);
            }

            stringBuilder.append( (i < tilesOnHand.size() - 1) ? formatedTile + ", " : formatedTile );

            if ( i == (tilesOnHand.size() / 2) - 1){
                stringBuilder.append("\n");
            }
        }

        return (name + "'s hand:\n" + stringBuilder);
    }

    public boolean hasWon(){
        return tilesOnHand.isEmpty();
    }
}
