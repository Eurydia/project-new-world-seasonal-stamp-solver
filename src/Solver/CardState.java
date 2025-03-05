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

    public int getState() {
        return state;
    }

    public List<Integer> getBits() {
        char[] bitCharArr = String.format("%16s", Integer.toBinaryString(state)).replace(' ', '0').toCharArray();
        List<Integer> bits = new ArrayList<>();
        for (char bit : bitCharArr) {
            bits.add(Integer.valueOf(bit));
        }
        return bits;
    }

    public int getFreeMoveCount() {
        return freeMoveCount;
    }

}
