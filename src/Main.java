import Solver.RandomNumberGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println(1 << 16);
        System.out.println(Math.pow(2, 16));
        System.out.println(Integer.valueOf(0b0000000000000001));
        var randomizer = new RandomNumberGenerator(0);

        System.out.println(randomizer.random(4, 0, 17));


    }
}
