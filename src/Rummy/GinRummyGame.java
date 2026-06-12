package Rummy;

import Cards.Card;
import Cards.DiscardPile;
import Cards.Deck;
import Cards.MeldValidator;
import Player.CardPlayer;
import Rummikub.Game;
import Rummikub.MoveLog;
import Rummikub.TurnManager;

import java.util.ArrayList;
import java.util.List;

public class GinRummyGame extends Game {

    private static final long serialVersionUID = 1L;

    private static final int INITIAL_HAND_SIZE = 10;
    private static final int MAX_KNOCK_DEADWOOD = 10;
    private static final int GIN_BONUS = 20;
    private static final int UNDERCUT_BONUS = 10;

    private Deck stock = new Deck(1, 0);
    private DiscardPile discardPile = new DiscardPile();
    private MoveLog moveLog = new MoveLog();

    private TurnManager turnManager;
    private CardPlayer[] players;

    public GinRummyGame(String[] playersName){

        players = new CardPlayer[playersName.length];

        for (int i = 0; i < playersName.length; i++){
            players[i] = new CardPlayer(playersName[i]);
        }

        turnManager = new TurnManager(playersName.length);
    }

    protected void setUpGame(){

        for (CardPlayer player : players){
            for (int i = 0; i < INITIAL_HAND_SIZE; i++){
                player.addCard(stock.drawCard());
            }
        }

        discardPile.discard(stock.drawCard());
    }

    protected void gameLoop(){

        while (running){

            int currentIndex = turnManager.getCurrentPlayerIndex();
            CardPlayer currPlayer = players[currentIndex];
            CardPlayer opponent = players[(currentIndex + 1) % players.length];

            console.println("\nTurn: " + currPlayer.getName());

            Card top = discardPile.peekTop();
            console.println("Discard pile top: " + (top == null ? "(empty)" : top.toString()));
            console.println(currPlayer.handToString());

            int drawChoice = showDrawMenu();

            if (drawChoice == 3){
                saveAndQuit();
                break;
            }

            boolean drew = doDraw(currPlayer, drawChoice);
            if (!drew) continue;

            discardOneCard(currPlayer);

            if (wantsToGoOut()){
                attemptToGoOut(currPlayer, opponent);
            }

            if (running){
                turnManager.nextTurn();
            }
        }
    }

    private int showDrawMenu(){
        console.println("\nDraw:\n" +
                "1. Draw a card from the stock\n" +
                "2. Take the top card from the discard pile\n" +
                "3. Save and quit");
        return console.inputInt(">");
    }

    private boolean doDraw(CardPlayer player, int drawChoice){

        switch (drawChoice){

            case 1:
                if (stock.isEmpty()){
                    console.println("The stock is empty.");
                    return false;
                }
                Card fromStock = stock.drawCard();
                player.addCard(fromStock);
                console.println("You drew: " + fromStock);
                moveLog.register(player.getName() + " drew from the stock");
                return true;

            case 2:
                Card fromDiscard = discardPile.takeTop();
                if (fromDiscard == null){
                    console.println("The discard pile is empty.");
                    return false;
                }
                player.addCard(fromDiscard);
                console.println("You took: " + fromDiscard);
                moveLog.register(player.getName() + " took " + fromDiscard + " from the discard pile");
                return true;

            default:
                console.println("Invalid option.");
                return false;
        }
    }

    private void discardOneCard(CardPlayer player){

        printHandWithIndices(player.getHand());

        int index = console.inputInt("Index of the card to discard: ");

        while (index < 0 || index >= player.getHand().size()){
            index = console.inputInt("Invalid index, try again: ");
        }

        Card discarded = player.getHand().get(index);
        player.removeCard(discarded);
        discardPile.discard(discarded);

        moveLog.register(player.getName() + " discarded " + discarded);
    }

    private boolean wantsToGoOut(){

        console.println("\n1. Knock / Gin\n2. End turn");
        int pick = console.inputInt(">");
        return pick == 1;
    }

    private void attemptToGoOut(CardPlayer player, CardPlayer opponent){

        console.println("\n" + player.getName() + ", declare your melds.");
        int playerDeadwood = declareMelds(player);

        if (playerDeadwood > MAX_KNOCK_DEADWOOD){
            console.println("You cannot go out: deadwood is " + playerDeadwood + " (must be 10 or less). Turn passes.");
            return;
        }

        console.println("\n" + opponent.getName() + ", declare your melds.");
        int opponentDeadwood = declareMelds(opponent);

        boolean gin = (playerDeadwood == 0);

        announceResult(player, opponent, playerDeadwood, opponentDeadwood, gin);

        console.println("\n" + moveLog.toString());
        running = false;
    }

    private void announceResult(CardPlayer player, CardPlayer opponent, int playerDeadwood, int opponentDeadwood, boolean gin){

        if (gin){
            int points = GIN_BONUS + opponentDeadwood;
            console.println("\n=== " + player.getName() + " went GIN and wins " + points + " points! ===");
            return;
        }

        if (playerDeadwood < opponentDeadwood){
            int points = opponentDeadwood - playerDeadwood;
            console.println("\n=== " + player.getName() + " knocked and wins " + points + " points! ===");
        } else {
            int points = (playerDeadwood - opponentDeadwood) + UNDERCUT_BONUS;
            console.println("\n=== " + opponent.getName() + " undercut and wins " + points + " points! ===");
        }
    }

    private int declareMelds(CardPlayer player){

        List<Card> remaining = new ArrayList<>(player.getHand());

        boolean done = false;

        while (!done){

            printHandWithIndices(remaining);
            String input = console.input("Enter card indices for a meld, or 'done': ");

            if (input.trim().equalsIgnoreCase("done")){
                done = true;
                continue;
            }

            List<Card> meld = readCards(remaining, input);

            if (meld == null) continue;

            if (MeldValidator.isValidMeld(meld)){
                remaining.removeAll(meld);
                console.println("Meld accepted.");
            } else {
                console.println("That is not a valid group or run.");
            }
        }

        return deadwoodValue(remaining);
    }

    private List<Card> readCards(List<Card> source, String input){

        String[] indices = input.trim().split(" ");
        List<Card> cards = new ArrayList<>();

        for (String indexStr : indices){

            int index = Integer.parseInt(indexStr);

            if (index < 0 || index >= source.size()){

                console.println("Index out of range: " + index);
                return null;
            }

            cards.add(source.get(index));
        }

        return cards;
    }

    private int deadwoodValue(List<Card> cards){

        int total = 0;

        for (Card card : cards){
            total += ginCardValue(card.getRank());
        }

        return total;
    }

    private int ginCardValue(int rank){

        if (rank == 1) return 1; // as
        if (rank >= 11) return 10; // J, Q, K
        return rank; // 2..10
    }

    private void printHandWithIndices(List<Card> hand){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hand.size(); i++){

            sb.append(i).append(":").append(hand.get(i).toString());
            if (i < hand.size() - 1) sb.append(", ");
        }

        console.println(sb.toString());
    }

    private void saveAndQuit(){

        String saveName = console.input("Save name: ");
        saver.save(this, saveName);

        console.println("Game saved as '" + saveName + "'.");
        running = false;
    }
}