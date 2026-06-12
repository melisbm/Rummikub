package Rummy;

import Cards.Card;
import Cards.Deck;
import Cards.DiscardPile;
import Cards.Meld;
import Cards.MeldValidator;
import Cards.Table;
import Player.CardPlayer;
import Rummikub.Game;
import Rummikub.MoveLog;
import Rummikub.TurnManager;

import java.util.ArrayList;
import java.util.List;

public class ArgentinianRummyGame extends Game {

    private static final long serialVersionUID = 1L;

    private static final int INITIAL_HAND_SIZE = 9;

    private Deck pozo;
    private Table table = new Table();
    private DiscardPile discardPile = new DiscardPile();
    private MoveLog moveLog = new MoveLog();

    private TurnManager turnManager;
    private CardPlayer[] players;

    public ArgentinianRummyGame(String[] playersName){

        players = new CardPlayer[playersName.length];

        for (int i = 0; i < playersName.length; i++){
            players[i] = new CardPlayer(playersName[i]);
        }

        if (playersName.length > 4){
            pozo = new Deck(2, 4);
        } else {
            pozo = new Deck(1, 2);
        }

        turnManager = new TurnManager(playersName.length);
    }

    protected void setUpGame(){

        for (CardPlayer player : players){
            for (int i = 0; i < INITIAL_HAND_SIZE; i++){
                player.addCard(pozo.drawCard());
            }
        }

        discardPile.discard(pozo.drawCard());
    }

    protected void gameLoop(){

        while (running){

            int currentIndex = turnManager.getCurrentPlayerIndex();
            CardPlayer currPlayer = players[currentIndex];

            console.println("\nTurn: " + currPlayer.getName() + "\n");
            console.println(table.toString());

            Card top = discardPile.peekTop();
            console.println("Top of discard pile: " + (top == null ? "(empty)" : top.toString()));
            console.println(currPlayer.handToString());

            console.println("\n1. Play your turn\n2. Save and quit");
            int choice = console.inputInt(">");

            if (choice == 2){
                saveAndQuit();
                continue;
            }

            drawPhase(currPlayer);
            console.println(currPlayer.handToString());

            layMeldsPhase(currPlayer);

            if (currPlayer.hasEmptyHand()){
                announceWinner(currPlayer);
                continue;
            }

            discardPhase(currPlayer);

            if (currPlayer.hasEmptyHand()){
                announceWinner(currPlayer);
                continue;
            }

            turnManager.nextTurn();
        }
    }

    private void drawPhase(CardPlayer player){

        Card top = discardPile.peekTop();

        console.println("\nDraw:");
        console.println("1. Take top of discard pile " + (top == null ? "(empty)" : top.toString()));
        console.println("2. Draw from the stock");

        int pick = console.inputInt(">");

        if (pick == 1 && top != null){

            Card taken = discardPile.takeTop();
            player.addCard(taken);
            moveLog.register(player.getName() + " took " + taken.toString() + " from the discard pile");
        }
        else {
            if (pozo.isEmpty()){
                console.println("The stock is empty.");
                return;
            }

            Card drawn = pozo.drawCard();
            player.addCard(drawn);
            moveLog.register(player.getName() + " drew a card from the stock");
        }
    }

    private void layMeldsPhase(CardPlayer player){

        boolean laying = true;

        while (laying && !player.getHand().isEmpty()){

            console.println("\n1. Lay a meld\n2. Done laying");
            int pick = console.inputInt(">");

            if (pick != 1){
                laying = false;
                break;
            }

            List<Card> selected = askPlayerForCards(player);

            if (MeldValidator.isValidMeld(selected)){

                Meld meld = new Meld(selected);
                table.addMeld(meld);

                player.removeCards(selected);
                moveLog.register(player.getName() + " laid down a meld: " + meld.toString());
            } else {
                console.println("That is not a valid group or run. Try again.");
            }
        }
    }

    private void discardPhase(CardPlayer player){

        List<Card> hand = player.getHand();

        printHandWithIndices(hand);
        int index = console.inputInt("Choose a card to discard: ");

        while (index < 0 || index >= hand.size()){
            index = console.inputInt("Invalid index, choose again: ");
        }

        Card discarded = hand.get(index);
        player.removeCard(discarded);
        discardPile.discard(discarded);

        moveLog.register(player.getName() + " discarded " + discarded.toString());
    }

    private void announceWinner(CardPlayer player){
        console.println("\n=== " + player.getName() + " cut and won the game! ===");
        console.println("\n" + moveLog.toString());
        running = false;
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

    private void printHandWithIndices(List<Card> cards){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cards.size(); i++){

            sb.append(i).append(":").append(cards.get(i).toString());
            if (i < cards.size() - 1) sb.append(", ");
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