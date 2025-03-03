package Solver;

import java.util.HashSet;
import java.util.Random;

public class RandomNumberGenerator {
    private final Random gen;

    public RandomNumberGenerator(long seed) {
        this.gen = new Random(seed);
    }

    public HashSet<Integer> random(int count, int minValue, int maxValueExclusive) {
        if (maxValueExclusive <= minValue) {
            throw new IllegalArgumentException();
        }

        if (maxValueExclusive - minValue < count) {
            throw new IllegalArgumentException();
        }

        var result = new HashSet<Integer>();

        while (result.size() < count) {
            result.add(this.gen.nextInt(minValue, maxValueExclusive));
        }
        return result;
    }

}
