package Cards;

import java.util.ArrayList;
import java.util.List;

public class MeldValidator {

    public static boolean isValidMeld(List<Card> cards){

        if (cards.size() < 3) return false;

        return isGroup(cards) || isRun(cards);
    }

    public static boolean isGroup(List<Card> cards){

        if (cards.size() < 3 || cards.size() > 4) return false;

        int groupRank = -1;
        List<Suit> usedSuits = new ArrayList<>();

        for (Card card : cards){

            if (card.isJoker()) continue;

            int cardRank = card.getRank();
            Suit cardSuit = card.getSuit();

            if (groupRank == -1) groupRank = cardRank;
            else if (cardRank != groupRank) return false;

            if (usedSuits.contains(cardSuit)) return false;
            usedSuits.add(cardSuit);
        }

        return true;
    }

    public static boolean isRun(List<Card> cards){

        if (cards.size() < 3) return false;

        List<Card> numberedCards = new ArrayList<>();
        int jokers = 0;

        for (Card card : cards){

            if (card.isJoker()) jokers++;
            else numberedCards.add(card);
        }

        if (numberedCards.isEmpty()) return false;

        Suit runSuit = numberedCards.get(0).getSuit();

        for (Card card : numberedCards){
            if (card.getSuit() != runSuit) return false;
        }

        sortByRank(numberedCards);

        for (int i = 1; i < numberedCards.size(); i++){

            int prev = numberedCards.get(i - 1).getRank();
            int curr = numberedCards.get(i).getRank();

            if (curr == prev) return false;

            int gap = curr - prev - 1;
            jokers -= gap;

            if (jokers < 0) return false;
        }

        return true;
    }

    private static void sortByRank(List<Card> cards){

        for (int i = 0; i < cards.size() - 1; i++){

            int minIndex = i;

            for (int j = i + 1; j < cards.size(); j++){
                if (cards.get(j).getRank() < cards.get(minIndex).getRank()){
                    minIndex = j;
                }
            }

            if (minIndex != i){
                Card temp = cards.get(i);
                cards.set(i, cards.get(minIndex));
                cards.set(minIndex, temp);
            }
        }
    }
}