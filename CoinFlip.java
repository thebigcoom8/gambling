package gambling.games;

import gambling.util.*;
import java.util.Scanner;

public class CoinFlip {
    // Takes the player's points, returns the updated points.
    public static int play(Scanner in, int point) {
        System.out.println("Would you like to play coin flip?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            int bet = Betting.getBet(in, point, "You have: " + point + " points, make a bet: ");

            if (bet != Betting.INVALID) {
                    point -= bet;       // take the bet
                    int pot = bet;      // money on the table

                    boolean flipping = true;
                    while (flipping) {
                    System.out.println("Heads or tails?");
                    String pick = in.nextLine().trim().toLowerCase();
                    String result = Math.random() < 0.5 ? "heads" : "tails";

                    if (pick.startsWith(result.substring(0, 1))) {
                        pot *= 2;
                        System.out.println("It was " + result + ", you win! The pot is now " + pot + ".");
                        System.out.println("Double or nothing? (yes to flip again, no to cash out)");
                        flipping = Input.isYes(in.nextLine());
                    } else {
                        System.out.println("It was " + result + ", you lose the whole pot!");
                        pot = 0;
                        flipping = false;
        }
    }

    point += pot;       // cash out (0 if you lost)
    System.out.println("You have: " + point + " points");
}

            System.out.println("Would you like to play again?");
            input = in.nextLine();
        }
        return point;
    }
}