package Rummikub;

import Player.Player;
import Rummikub.Tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Play> plays = new ArrayList<>();

    public Board(){  }

    public void addNewPlay(Play newPlay){
        plays.add(newPlay);
    }

    public void replaceSet(int index, Play newPlay){
        plays.set(index, newPlay);
    }

    public List<Play> getPlays(){
        return plays;
    }

    public boolean isEmpty(){
        return plays.isEmpty();
    }

    public String toString(){

        if (plays.isEmpty()) return "Board is empty.";

        StringBuilder sb = new StringBuilder("Board:\n");

        for (int i = 0; i < plays.size(); i++){

            Play play = plays.get(i);
            List<Tile> playTiles = play.getTiles();

            sb.append(" ").append(i).append(": ");

            for (int j = 0; j < play.size(); j++) {
                Tile tile = playTiles.get(j);
                String tileStr = tile.isJoker() ? "[JK]" : "[" + tile.getNumber() + "|" + tile.getColor() + "]";
                sb.append(tileStr);
                if (j < playTiles.size() - 1) sb.append(", ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}