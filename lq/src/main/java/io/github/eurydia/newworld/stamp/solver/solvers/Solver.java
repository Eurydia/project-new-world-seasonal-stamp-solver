package io.github.eurydia.newworld.stamp.solver.solvers;

import main.java.io.github.eurydia.newworld.model.CardState;

import java.util.List;

public interface Solver {
    List<CardState> solve(CardState card);
}
