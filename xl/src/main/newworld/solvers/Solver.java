package main.java.io.github.eurydia.newworld.solvers;

import main.java.io.github.eurydia.newworld.model.CardState;

import java.util.List;

public interface Solver {
    List<CardState> solve(CardState card);
}
