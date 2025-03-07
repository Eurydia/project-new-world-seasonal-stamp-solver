package main.java.io.github.eurydia.newworld.solvers;

import main.java.io.github.eurydia.newworld.model.CardState;

import java.util.ArrayList;
import java.util.List;

public class GreedySolver implements Solver {
    private List<Integer> getRowWithMissingCell(int missingCount, List<Boolean> states) {
        List<Integer> result = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            List<Integer> missing = new ArrayList<>();
            for (int colIndex = 0; colIndex < 4; colIndex++) {
                int pos = (rowIndex * 4) + colIndex;
                if (!states.get(pos)) {
                    missing.add(pos);
                }
            }
            if (missing.size() == missingCount) {
                result.addAll(missing);
            }
        }
        return result;
    }

    private List<Integer> getColWithMissingCell(int missingCount, List<Boolean> states) {
        List<Integer> result = new ArrayList<>();
        for (int colIndex = 0; colIndex < 4; colIndex++) {
            List<Integer> missing = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
                int pos = colIndex + (rowIndex * 4);
                if (!states.get(pos)) {
                    missing.add(pos);
                }
            }
            if (missing.size() == missingCount) {
                result.addAll(missing);
            }
        }
        return result;
    }

    private List<Integer> getDiaWithMissingCell(int missingCount, List<Boolean> states) {
        List<Integer> result = new ArrayList<>();
        List<Integer> missing = new ArrayList<>();
        for (int i = 3; i < 13; i += 3) {
            if (!states.get(i)) {
                missing.add(i);
            }
        }
        if (missing.size() == missingCount) {
            result.addAll(missing);
        }
        missing.clear();
        for (int i = 0; i < 16; i += 5) {
            if (!states.get(i)) {
                missing.add(i);
            }
        }
        if (missing.size() == missingCount) {
            result.addAll(missing);
        }
        return result;
    }


    @Override
    public List<CardState> solve(CardState card) {
        List<CardState> queue = new ArrayList<>(List.of(card));
        List<CardState> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            CardState curr = queue.removeFirst();

            if (curr.getMovesLeft() == 0) {
                continue;
            }

            if (curr.isDone() && curr.getMovesLeft() == 3) {
                result.add(curr);
                break;
            }

            List<Boolean> states = curr.getStates();

            List<Integer> moves = getRowWithMissingCell(1, states);
            moves.addAll(getColWithMissingCell(1, states));
            moves.addAll(getDiaWithMissingCell(1, states));

            if (moves.isEmpty()) {
                moves.addAll(getRowWithMissingCell(2, states));
                moves.addAll(getColWithMissingCell(2, states));
                moves.addAll(getDiaWithMissingCell(2, states));
            }

            for (int move : moves) {
                queue.add(curr.stampCard(move));
            }

        }
        return result;
    }
}
