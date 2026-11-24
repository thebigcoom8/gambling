package gambling.util;

import java.util.Scanner;

public class Betting {
    public static final int INVALID = -1;

    /**
     * Prompts for a bet and validates it.
     * Returns the bet, or INVALID if it was bad (an error message is printed).
     * If allowCheat is true, -69 is passed through as CHEAT.
     * This does NOT subtract the bet; the caller does that.
     */
    public static int getBet(Scanner in, int points, String prompt, boolean allowCheat) {
        System.out.print(prompt);
        int bet;
        try {
            bet = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid bet!");
            return INVALID;
        }

        if (bet <= 0 || bet > points) {
            System.out.println("Invalid bet!");
            return INVALID;
        }
        return bet;
    }

    public static int getBet(Scanner in, int points, String prompt) {
        return getBet(in, points, prompt, false);
    }
}
