package io.github.eurydia.stamp.solver.solvers;


import io.github.eurydia.stamp.solver.model.CardState;

import java.util.List;

public interface Solver {
    List<CardState> solve(CardState card);
}
