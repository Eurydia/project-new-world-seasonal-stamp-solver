package io.github.eurydia.stamp.solver;

import io.github.eurydia.stamp.solver.model.CardState;
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
    private static final Solver solver = new GreedySolver();
    private static final Logger log = LogManager.getLogger(Server.class);

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
//        var cardState = new CardState(List.of(
//                0, 0, 1, 0,
//                0, 0, 0, 0,
//                1, 1, 1, 0,
//                0, 0, 0, 0
//
//        ), 3);
    }
    @RequestMapping(value =  "/", method =  RequestMethod.GET)
    public @ResponseBody CardState getSolution(@RequestParam("state")  int  stateId, @RequestParam("moves") int moves) {

        List<Integer> states = new ArrayList<>();
        String stateIdBinary = String.format("%16s", Integer.toBinaryString(stateId)).replace(' ','0' );
        for (int i =0; i <16; i ++) {
            states.add(Integer.parseInt(String.valueOf(stateIdBinary.charAt(i))));
        }
        CardState initState = new CardState(states, moves);
        Instant start = Instant.now();
        log.log(Level.INFO, "Started calculation");
        List<CardState> solutions = solver.solve(initState);
        Instant finish = Instant.now();
        log.log(Level.INFO, String.format("Done in %s",Duration.between(start,finish).toString()));
        return solutions.isEmpty() ? null : solutions.getFirst();
    }
}
