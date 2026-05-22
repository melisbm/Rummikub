package Rummikub;

public class TurnManager {

    private int playerCount;
    private int currentPlayerIndex;

    public TurnManager(int playerCount) {
        this.playerCount = playerCount;
        this.currentPlayerIndex = 0;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void nextTurn() {
        currentPlayerIndex++;

        if (currentPlayerIndex >= playerCount) {
            currentPlayerIndex = 0;
        }
    }
}