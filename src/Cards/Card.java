package Cards;

import java.io.Serializable;

public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rank;
    private Suit suit;
    private boolean isJoker;

    public Card(int rank, Suit suit){

        this.rank = rank;
        this.suit = suit;
        isJoker = false;
    }

    public Card(){
        isJoker = true;
    }

    public Suit getSuit(){
        return suit;
    }

    public boolean isJoker(){
        return isJoker;
    }

    public int getRank(){
        if (isJoker){
            throw new UnsupportedOperationException("Cannot get the rank from a Joker.");
        }
        return rank;
    }

    public String toString(){
        if (isJoker) return "[JK]";
        return "[" + rank + "|" + suit + "]";
    }
}