package io.github.eurydia.stamp.solver;

import io.github.eurydia.stamp.solver.model.CardState;
import io.github.eurydia.stamp.solver.services.SolverService;
import io.github.eurydia.stamp.solver.solvers.GreedySolver;
import io.github.eurydia.stamp.solver.solvers.Solver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.Card;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@SpringBootApplication
public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static final SolverService solverService = new SolverService();
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @GetMapping(value =  "/")
    public @ResponseBody CardState getSolution(@RequestParam("state")  int  stateId, @RequestParam("moves") int moves) {
        List<CardState> solutions = solverService.solve(stateId, moves);
        return solutions.isEmpty() ? null : solutions.getFirst();
    }
}
