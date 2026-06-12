package Player;

import Cards.Card;

import java.util.ArrayList;
import java.util.List;

public class CardPlayer extends Player {

    private List<Card> hand = new ArrayList<>();

    public CardPlayer(String name){
        this.name = name;
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void removeCard(Card card){
        hand.remove(card);
    }

    public void removeCards(List<Card> cardsToRemove){
        hand.removeAll(cardsToRemove);
    }

    public List<Card> getHand(){
        return hand;
    }

    public boolean hasEmptyHand(){
        return hand.isEmpty();
    }

    public String handToString(){

        StringBuilder sb = new StringBuilder(name + "'s hand:\n");

        for (int i = 0; i < hand.size(); i++){
            sb.append(hand.get(i).toString());
            if (i < hand.size() - 1) sb.append(", ");
        }

        return sb.toString();
    }
}