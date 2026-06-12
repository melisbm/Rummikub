package Rummy;

import Cards.Card;
import Cards.Deck;
import Cards.Meld;
import Cards.MeldValidator;
import Cards.Table;
import Player.CardPlayer;
import Rummikub.Game;
import Rummikub.MoveLog;
import Rummikub.TurnManager;

import java.util.ArrayList;
import java.util.List;

public class RummyGame extends Game {

    private static final long serialVersionUID = 1L;

    private static final int INITIAL_HAND_SIZE = 14;
    private static final int MINIMUM_FIRST_MELD_POINTS = 30;

    private Deck pozo = new Deck(2, 2);
    private Table table = new Table();
    private MoveLog moveLog = new MoveLog();

    private TurnManager turnManager;
    private CardPlayer[] players;
    private boolean[] hasMelded;

    public RummyGame(String[] playersName){

        players = new CardPlayer[playersName.length];

        for (int i = 0; i < playersName.length; i++){
            players[i] = new CardPlayer(playersName[i]);
        }

        hasMelded = new boolean[playersName.length];
        turnManager = new TurnManager(playersName.length);
    }

    protected void setUpGame(){

        for (CardPlayer player : players){

            for (int i = 0; i < INITIAL_HAND_SIZE; i++){
                player.addCard(pozo.drawCard());
            }
        }
    }

    protected void gameLoop(){

        while (running){

            int currentIndex = turnManager.getCurrentPlayerIndex();
            CardPlayer currPlayer = players[currentIndex];

            console.println("\nTurn: " + currPlayer.getName() + "\n");
            console.println(table.toString());
            console.println(currPlayer.handToString());

            int optionPick = showOptionsMenu();

            switch (optionPick){

                case 1:

                    if (!pozo.isEmpty()){
                        Card drawn = pozo.drawCard();
                        currPlayer.addCard(drawn);
                        moveLog.register(currPlayer.getName() + " drew a card from the pozo");
                        turnManager.nextTurn();
                    } else {
                        console.println("The pozo is empty.");
                    }
                    break;

                case 2:
                    layDownMeld(currPlayer, currentIndex);
                    break;

                case 3:
                    console.println("Modifying melds is not available yet.");
                    break;

                case 4:
                    turnManager.nextTurn();
                    break;

                case 5:
                    saveAndQuit();
                    break;

                default:
                    console.println("Invalid option.");
            }

            if (currPlayer.hasEmptyHand()){
                console.println("\n=== " + currPlayer.getName() + " has won the game! ===");
                console.println("\n" + moveLog.toString());
                running = false;
            }
        }
    }

    private int showOptionsMenu(){
        console.println("\nOptions:\n" +
                "1. Draw a card from the pozo (ends your turn)\n" +
                "2. Lay down a meld\n" +
                "3. Modify existing meld\n" +
                "4. End turn\n" +
                "5. Save and quit");
        return console.inputInt(">");
    }

    private void layDownMeld(CardPlayer player, int playerIndex){

        List<Card> selectedCards = askPlayerForCards(player);

        if (!isValidRummyMeld(selectedCards)){
            console.println("That is not a valid group or run. Try again.");
            return;
        }

        Meld meld = new Meld(selectedCards);

        if (!hasMelded[playerIndex] && meldValue(meld) < MINIMUM_FIRST_MELD_POINTS){
            console.println("Your first meld must be worth at least " + MINIMUM_FIRST_MELD_POINTS + " points.");
            return;
        }

        table.addMeld(meld);
        player.removeCards(selectedCards);
        hasMelded[playerIndex] = true;

        moveLog.register(player.getName() + " laid down a meld: " + meld.toString());
    }

    private boolean isValidRummyMeld(List<Card> cards){

        if (MeldValidator.isGroup(cards)) return true;

        if (MeldValidator.isRun(cards) && cards.size() >= 4) return true;

        return false;
    }

    private List<Card> askPlayerForCards(CardPlayer player){

        List<Card> hand = player.getHand();

        printHandWithIndices(hand);

        console.println("Enter card indices separated by spaces: ");
        String input = console.input("");

        String[] indices = input.trim().split(" ");

        List<Card> selectedCards = new ArrayList<>();

        for (String indexStr : indices){

            int index = Integer.parseInt(indexStr);
            Card selectedCard = hand.get(index);
            selectedCards.add(selectedCard);
        }

        return selectedCards;
    }

    private void printHandWithIndices(List<Card> hand){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hand.size(); i++){
            sb.append(i).append(":").append(hand.get(i).toString());
            if (i < hand.size() - 1) sb.append(", ");
        }

        console.println(sb.toString());
    }

    private int meldValue(Meld meld){

        List<Card> cards = meld.getCards();

        int referenceRank = -1;
        for (Card card : cards){
            if (!card.isJoker()){
                referenceRank = card.getRank();
                break;
            }
        }

        int total = 0;
        for (Card card : cards){
            if (card.isJoker()){
                total += (referenceRank == -1) ? 0 : cardValue(referenceRank);
            } else {
                total += cardValue(card.getRank());
            }
        }

        return total;
    }

    private int cardValue(int rank){
        return (rank <= 7) ? 5 : 10;
    }

    private void saveAndQuit(){

        String saveName = console.input("Save name: ");
        saver.save(this, saveName);
        console.println("Game saved as '" + saveName + "'.");
        running = false;
    }
}