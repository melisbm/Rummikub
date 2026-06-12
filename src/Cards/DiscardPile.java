package Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscardPile implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Card> cards = new ArrayList<>();

    public void discard(Card card){
        cards.add(card);
    }

    public Card takeTop(){

        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    public Card peekTop(){

        if (cards.isEmpty()) return null;
        return cards.get(cards.size() - 1);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}