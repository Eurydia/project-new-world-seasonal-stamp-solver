package Solver;

import java.util.ArrayList;
import java.util.List;

public class CardState {
    private static final int STATE_COUNT = 65536;
    private static final int LAST_STATE = STATE_COUNT - 1;
    private final int state;
    private final int freeMoveCount;

    public CardState(int state, int freeMoveCount) {
        if (freeMoveCount > 3 || freeMoveCount < 0) {
            throw new IllegalArgumentException();
        }

        if (state > STATE_COUNT || state < 0) {
            throw new IllegalArgumentException();
        }

        this.state = state;
        this.freeMoveCount = freeMoveCount;
    }

    @Override
    public String toString() {
        String bitRepr = String.format("%16s", Integer.toBinaryString(this.state)).replace(' ', '0');
        return String.format("state=%s", bitRepr);
    }

    public List<CardState> getValidMoves() {
        if (freeMoveCount == 0) {
            return List.of(this);
        }

        List<CardState> possibleNextMoves = new ArrayList<>();
        char[] bits = String.format("%16s", Integer.toBinaryString(this.state)).replace(' ', '0').toCharArray();
        for (int i = 0; i < 16; i++) {
            if (bits[i] == '0') {
                possibleNextMoves.add(this.stampAt(i / 4, i % 4));
            }
        }
        return possibleNextMoves;
    }

    public CardState stampAt(int row, int col) {
        int pos = (row * 4) + col;
        char[] bits = String.format("%16s", Integer.toBinaryString(this.state)).replace(' ', '0').toCharArray();

        if (bits[pos] == '1') {
            return new CardState(this.state, freeMoveCount - 1);
        }
        bits[pos] = '1';

        CardState nextState = new CardState(Integer.parseInt(new String(bits), 2), this.freeMoveCount - 1);
        return nextState;
    }

}
