package gambling.games;

import gambling.util.*;
import java.util.Scanner;

public class Craps {
    // Plays craps and returns the player's updated points.
    public static int play(Scanner in, int point) {
        System.out.println("Would you like to play crap?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            int bet = Betting.getBet(in, point, "You have: " + point + " points, make your bet: ");

            if (bet != Betting.INVALID) {
                point -= bet;
                point += playRound(bet);
                System.out.println("You have: " + point + " points.");
            }

            System.out.println("Would you like to play again?");
            input = in.nextLine();
        }
        return point;
    }

    private static int rollDie() {
        return (int) (6 * Math.random()) + 1;
    }

    // Plays one round and returns the payout (0 on a loss).
    private static int playRound(int bet) {
        int dice1 = rollDie();
        int dice2 = rollDie();

        System.out.println("Dice 1: " + dice1);
        System.out.println("Dice 2: " + dice2);
        int total = dice1 + dice2;
        System.out.println("Your total is " + total);

        if (total == 7 || total == 11) {
            System.out.println("You win!");
            return bet * 2;
        }
        if (total == 2 || total == 3 || total == 12) {
            System.out.println("You lose!");
            return 0;
        }

        // Rolled 4, 5, 6, 8, 9, or 10: try to match 'total' before a 7 (max 8 re-rolls)
        for (int i = 8; i > 0; i--) {
            int total2 = rollDie() + rollDie();
            System.out.println("Re-roll: " + total2);

            if (total2 == total) {
                System.out.println("You win!");
                return bet * 2;
            } else if (total2 == 7) {
                System.out.println("you rolled a 7, you lose!");
                return 0;
            }
        }
        System.out.println("Out of rolls, you lose!");
        return 0;
    }
}
