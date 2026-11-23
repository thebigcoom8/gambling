package gambling.util;

import gambling.Story;
import java.util.Scanner;

public class EndOfLoop {
    private static final long GOAL = 1_000_000_000L;
    private static final int LAST_DAY = 60;

    /** Returns true if the game is over and the caller should break. */
    public static boolean check(int count, long point, int debt, Scanner userin) {
        if (count == LAST_DAY) {
            System.out.println("it's day 60, you have: " + point + " points");

            if (point >= GOAL) {
                System.out.println("you made enough you can afford the payment, good job!");
                Story.play(userin, count, true);
            } else {
                System.out.println("you don't have enough, your mother is going to die. you had: "
                        + point + ", you needed: " + (GOAL - point) + " more points.");
            }
            return true;
        }

        if (debt >= 1500 && point <= 0) {
            System.out.println("you lost, on day: " + count + ", your mother will die now.");
            return true;
        }

        return false;
    }
}