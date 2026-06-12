package Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Card> cards = new ArrayList<>();

    public Deck(int numberOfDecks, int jokers){

        for (int i = 0; i < numberOfDecks; i++){

            for (Suit suit : Suit.values()){

                for (int rank = 1; rank <= 13; rank++){
                    cards.add(new Card(rank, suit));
                }
            }
        }

        for (int i = 0; i < jokers; i++){
            cards.add(new Card());
        }
    }

    public Card drawCard(){

        int randomIndex = (int) ( Math.random() * cards.size() );
        Card card = cards.get(randomIndex);

        cards.remove(randomIndex);

        return card;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int size(){
        return cards.size();
    }
}