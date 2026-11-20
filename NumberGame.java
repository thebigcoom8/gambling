package gambling.games;

import gambling.util.*;
import java.util.Scanner;

public class NumberGame {
    // Plays the number game and returns the player's updated points.
    // showIntro is true the first time the player picks this game.
    public static int play(Scanner in, int point, boolean showIntro) {
        if (showIntro) {
            System.out.println("Pick a number from 1 to 10. You get 3 guesses, a win pays 3x your bet.");
        }

        System.out.println("Would you like to play a number game?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            int bet = Betting.getBet(in, point, "You have: " + point + " points, make your bet: ");

            if (bet != Betting.INVALID) {
                point -= bet;                               // take the bet
                int rn = (int) (10 * Math.random()) + 1;
                int guesses = 3;
                boolean won = false;

                while (guesses > 0 && !won) {
                    System.out.println("Guess a number from 1 to 10 (" + guesses + " guesses left):");
                    int yg;
                    try {
                        yg = Integer.parseInt(in.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a number.");
                        continue;                           // doesn't use up a guess
                    }

                    if (yg == -67) {                        // cheat still works
                        yg = rn;
                    }

                    if (yg == rn) {
                        won = true;
                    } else {
                        guesses--;
                        System.out.println("Haha you're wrong!");
                    }
                }

                if (won) {
                    point += bet * 3;                       // bet back plus winnings
                    System.out.println("Good job! The number was " + rn + ". You now have: " + point + " points");
                } else {
                    System.out.println("Out of guesses, the number was " + rn + ". You have: " + point + " points");
                }
            }

            System.out.println("Would you like to play a number game again?");
            input = in.nextLine();
        }
        return point;
    }
}