package Solver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CardStateFactory {

    private static boolean isRowFilled(int pos, char[] bits) {

        for (int i = row * 4; i < (row * 4) + 4; i++) {
            if (bits[i] == '0') {
                return false;
            }
        }
        return true;
    }

    private static boolean isColFilled(int pos, char[] bits) {
//        for (int i = col; i < 12 + col; i += 4) {
//            if (bits[i] == '0') {
//                return false;
//            }
//        }
//        return true;
    }

    private static boolean isDiagFiveFilled(int pos, char[] bits) {

    }


    public static List<CardState> getAllMoves(CardState card) {
        List<CardState> possibleNextMoves = new ArrayList<CardState>();
        if (card.getFreeMoveCount() == 0) {
            return possibleNextMoves;
        }

        char[] bits = card.getBits();
        for (int pos = 0; pos < 16; pos++) {
            if (bits[pos] == '0') {
                possibleNextMoves.add(stampCardAt(card, pos));
            }
        }
        return possibleNextMoves;
    }

    public static CardState stampCardAt(CardState card, int pos) {
        if (card.getFreeMoveCount() == 0) {
            throw new IllegalStateException();
        }
        if (pos < 0 || pos > 15) {
            throw new IllegalArgumentException();
        }
        List<Integer> bits = card.getBits();
        if (bits.get(pos) == '1') {
            throw new IllegalStateException();
        }
        bits.set(pos, 1);

        int nextMoveCount = card.getFreeMoveCount() - 1;
        CardState nextState = new CardState(, 2), nextMoveCount);
        return nextState;
    }
}
