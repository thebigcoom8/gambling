package gambling.games;

import gambling.util.*;
import java.util.Scanner;

public class Slots {
    // Plays slots and returns the player's updated points.
    public static int play(Scanner in, int point) {
        System.out.println("would you play slots?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            int bet = Betting.getBet(in, point, "You have: " + point + " points, make a bet: ", true);

            if (bet != Betting.INVALID) {
                boolean cheat = (bet == Betting.CHEAT);
                if (cheat) {
                    point -= bet * 2; // original behavior: subtracting -69 twice
                    System.out.println("You win!... kinda");
                } else {
                    point -= bet;
                }

                int[] middleRow = spin();
                int r1 = middleRow[0];
                int r2 = middleRow[1];
                int r3 = middleRow[2];

                int matchedNum = 0;
                if (r1 == r2 || r1 == r3) {
                    matchedNum = r1;
                } else if (r2 == r3) {
                    matchedNum = r2;
                }

                if (cheat) {
                    // no payout check on the cheat bet
                } else if (r1 == r2 && r2 == r3) {
                    point += bet * 10;
                    System.out.println("You hit the jackpot! You have: " + point + " points");
                } else if (matchedNum >= 5) {
                    point += bet * 2;
                    System.out.println("You win! (high pair) You have: " + point + " points");
                } else {
                    System.out.println("You lost the most you can, you have: " + point + " points");
                }
            }

            System.out.println("Would you like to play slots again?");
            input = in.nextLine();
        }
        return point;
    }

    // Prints the 3x3 grid and returns the middle row (the payline).
    private static int[] spin() {
        int[] middleRow = new int[3];
        for (int row = 0; row < 3; row++) {
            System.out.print(row == 1 ? "-> " : "   ");

            for (int col = 0; col < 3; col++) {
                int num = (int) (7 * Math.random()) + 1;
                System.out.print("[" + num + "] ");
                if (row == 1) {
                    middleRow[col] = num;
                }
            }
            System.out.println();
        }
        return middleRow;
    }
}
