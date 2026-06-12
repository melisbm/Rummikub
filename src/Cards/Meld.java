package Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meld implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Card> cards = new ArrayList<>();

    public Meld(){ }

    public Meld(List<Card> cards){
        this.cards = cards;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public List<Card> getCards(){
        return cards;
    }

    public int size(){
        return cards.size();
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cards.size(); i++){

            sb.append(cards.get(i).toString());
            if (i < cards.size() - 1) sb.append(", ");
        }

        return sb.toString();
    }
}