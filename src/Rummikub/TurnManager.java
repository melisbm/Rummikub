package Rummikub;

import java.io.Serializable;

public class TurnManager implements Serializable {

    private static final long serialVersionUID = 1L;

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