import java.util.Scanner;
import java.util.Set;

public class Roulette {
    private static final Set<Integer> REDS = Set.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);

    // Plays roulette and returns the player's updated points.
    public static int play(Scanner in, int point) {
        System.out.println("would you like to play roulette?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            if (point <= 0) {
                System.out.println("you're out of points!");
                break;
            }

            // 0-36 are normal numbers, 37 represents "00"
            int rn = (int) (38 * Math.random());
            boolean green = (rn == 0 || rn == 37);
            String color = green ? "green" : (REDS.contains(rn) ? "red" : "black");
            String shown = (rn == 37) ? "00" : String.valueOf(rn);

            System.out.println("would you like to bet on color or number?");
            String bt = in.nextLine().trim().toLowerCase();

            int bet = Betting.getBet(in, point, "you have: " + point + " points. How much would you like to bet: ");
            if (bet == Betting.INVALID) {
                System.out.println("play again?");
                input = in.nextLine();
                continue;
            }
            point -= bet;

            if (bt.startsWith("c")) {
                System.out.println("what color (red/black/green):");
                String bettype = in.nextLine().trim().toLowerCase();

                if (bettype.startsWith("r") || bettype.startsWith("b") || bettype.startsWith("g")) {
                    String picked = bettype.startsWith("r") ? "red" : (bettype.startsWith("b") ? "black" : "green");
                    if (color.equals(picked)) {
                        // red/black pay 1:1 (bet * 2 back); green (0 or 00) is 2 of 38 slots, so it pays 17:1 (bet * 18 back)
                        point += picked.equals("green") ? bet * 18 : bet * 2;
                        System.out.println("you won, number was " + shown + " (" + color + "). you have: " + point + " points");
                    } else {
                        System.out.println("you lost, number was " + shown + " (" + color + "). you have: " + point + " points");
                    }
                } else {
                    point += bet; // refund on invalid choice
                    System.out.println("invalid color, bet refunded.");
                }
            } else if (bt.startsWith("n")) {
                System.out.println("what number (0-36, or 37 for 00):");
                int pick = Integer.parseInt(in.nextLine().trim());

                if (pick == rn) {
                    point += bet * 36; // 35:1 payout plus your bet back
                    System.out.println("you won, number was " + shown + ". you have: " + point + " points");
                } else {
                    System.out.println("you lost, number was " + shown + " (" + color + "). you have: " + point + " points");
                }
            } else {
                point += bet; // refund
                System.out.println("invalid bet type, bet refunded.");
            }

            System.out.println("would you like to play again?");
            input = in.nextLine();
        }
        return point;
    }
}