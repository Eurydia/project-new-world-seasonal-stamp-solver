import Solver.CardState;
import Solver.RandomNumberGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println(1 << 16);
        System.out.println(Math.pow(2, 16));
        System.out.println(Integer.valueOf(0b0000000000000001));
        var randomizer = new RandomNumberGenerator(0
        );

        System.out.println(String.format("%" + 16 + "s", Integer.toBinaryString(12)).replace(' ', '0'));
        var cardState = new CardState(60000, 3);
        var moves = cardState.getValidMoves();
        System.out.println(moves);
    }
}
