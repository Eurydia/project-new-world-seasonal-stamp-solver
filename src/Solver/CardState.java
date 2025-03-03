package Solver;

public class CardState {
    private static final int STATE_COUNT = 1 << 16;
    private final int state;
    private final byte freeMoveCount;

    public CardState(int state, byte freeMoveCount) {
        if (freeMoveCount > 3 || freeMoveCount < 0) {
            throw new IllegalArgumentException();
        }

        if (state > STATE_COUNT || state < 0) {
            throw new IllegalArgumentException();
        }

        this.state = state;
        this.freeMoveCount = freeMoveCount;
    }
}
