package io.github.eurydia.stamp.solver.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CardState {
    private static final int STATE_COUNT = 65536;
    private static final int LAST_STATE = STATE_COUNT - 1;
    private static final Logger log = LogManager.getLogger(CardState.class);
    private final int movesLeft;
    private final List<Boolean> states;
    private final List<CardStateHistory> history;

    public CardState(List<Integer> states, int movesLeft) {
        if (movesLeft > 3 || movesLeft < 0) {
            throw new IllegalArgumentException();
        }
        if (states.size() != 16) {
            throw new IllegalArgumentException();
        }

        this.movesLeft = movesLeft;
        this.states = new ArrayList<>();
        for (int state : states) {
            if (state != 0 && state != 1) {
                throw new IllegalArgumentException();
            }
            this.states.add(state == 1);
        }
        history = new ArrayList<>();
    }

    public CardState(int stateId, int movesLeft) {
        if (stateId < 0 || stateId > LAST_STATE) {
            throw new IllegalArgumentException();
        }
        if (movesLeft < 0 || movesLeft > 3) {
            throw  new IllegalArgumentException();
        }

        states = new ArrayList<>();
        String stateIdBinary = String.format("%16s", Integer.toBinaryString(stateId)).replace(' ','0' );
        for (int i =0; i <16; i ++) {
            states.add(stateIdBinary.charAt(i) == '1');
        }
        this.movesLeft =  movesLeft;
        history = new ArrayList<>();
    }

    private CardState(List<Boolean> states, int movesLeft, List<CardStateHistory> history, int lastestMove) {
        if (movesLeft > 3 || movesLeft < 0) {
            throw new IllegalArgumentException();
        }
        if (states.size() != 16) {
            throw new IllegalArgumentException();
        }

        this.movesLeft = movesLeft;
        this.states = new ArrayList<>(states);
        this.history = new ArrayList<>(history);
        this.history.add(new CardStateHistory(getStateId(), movesLeft,lastestMove));
    }

    private static int posToRow(int pos) {
        return (pos / 4) * 4;
    }

    private static int posToCol(int pos) {
        return pos % 4;
    }

    private static boolean isRowDone(int pos, List<Boolean> states) {
        int row = posToRow(pos);
        for (int i = row; i < row + 4; i++) {
            if (!states.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isColDone(int pos, List<Boolean> states) {
        int col = posToCol(pos);
        for (int i = col; i < 13 + col; i += 4) {
            if (!states.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDiaFiveDone(List<Boolean> states) {
        for (int i = 0; i < 16; i += 5) {
            if (!states.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDiaThreeDone(List<Boolean> states) {
        for (int i = 3; i < 13; i += 3) {
            if (!states.get(i)) {
                return false;
            }
        }
        return true;
    }

    public List<CardStateHistory> getHistory() {
        return new ArrayList<>(history);
    }

    public boolean isDone() {
        return getStateId() == LAST_STATE;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public List<Boolean> getStates() {
        return new ArrayList<>(states);
    }

    public int getStateId() {
        StringBuilder stateIdBinary = new StringBuilder();
        for (boolean state : states) {
            stateIdBinary.append(state ? '1' : '0');

        }
        return Integer.parseInt(stateIdBinary.toString(), 2);
    }

    public CardState stampCard(int pos) {
        if (pos < 0 || pos > 16) {
            throw new IllegalArgumentException();
        }
        if (states.get(pos)) {
            throw new IllegalStateException();
        }
        if (this.movesLeft == 0) {
            throw new IllegalArgumentException();
        }

        int nextMoveCount = movesLeft - 1;
        List<Boolean> nextStates = new ArrayList<>(states);
        nextStates.set(pos, true);
        if (isColDone(pos, nextStates)) {
            nextMoveCount++;
        }
        if (isRowDone(pos, nextStates)) {
            nextMoveCount++;
        }
        if (isDiaFiveDone(nextStates) && pos % 5 == 0) {
            nextMoveCount++;
        }
        if (isDiaThreeDone(nextStates) && pos % 3 == 0 && pos > 0) {
            nextMoveCount++;
        }

        nextMoveCount = Math.min(nextMoveCount, 3);
        return new CardState(nextStates, nextMoveCount, history, pos);

    }

    @Override
    public String toString() {
        return "CardState{" +
                "movesLeft=" + movesLeft +
                ", states=" + getStateId() +
                ", history=" + history +
                '}';
    }
}
