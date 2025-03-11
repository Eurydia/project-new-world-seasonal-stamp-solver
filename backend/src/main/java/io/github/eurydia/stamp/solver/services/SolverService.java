package io.github.eurydia.stamp.solver.services;

import io.github.eurydia.stamp.solver.model.CardState;
import io.github.eurydia.stamp.solver.solvers.GreedySolver;
import io.github.eurydia.stamp.solver.solvers.Solver;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SolverService {
    private final Solver solver = new GreedySolver();
    public List<CardState> solve(int stateId, int moves) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<CardState>> future = executor.submit(() -> {
            CardState initState = new CardState(stateId, moves);
            return solver.solve(initState);
        });

        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            executor.shutdownNow();
            throw new RuntimeException(e);
        }
    }
}
