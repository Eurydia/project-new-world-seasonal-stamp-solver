package io.github.eurydia.stamp.solver.model;

public class CardStateHistory {
    public int getStateId() {
        return stateId;
    }

    public int getMoves() {
        return moves;
    }

    public int getLastPos() {
        return lastPos;
    }

    private final int stateId;
    private final int moves;
    private final int lastPos;

    public CardStateHistory(int stateId, int moves, int pos) {
        this.stateId = stateId;
        this.moves = moves;
        this.lastPos = pos;
    }

    public CardStateHistory(CardState state, int lastPos) {
        stateId = state.getStateId();
        moves = state.getMovesLeft();
        this.lastPos = lastPos;
    }


}
