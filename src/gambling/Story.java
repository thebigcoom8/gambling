package gambling;

import java.util.Scanner;

public class Story {
    public static void play(Scanner userin, int count) {
        if (count == 1) {
            intro(userin);
        }
    }

    private static void intro(Scanner in) {
        beat(in, "Doctor: I'm sorry to tell you this, G.B., but your mother has cancer.\nShe is going to die by the end of the year.");
        beat(in, "She's in a coma right now.");
        beat(in, "Hours later, you're scrolling on a dark web marketplace and see a listing.\n$1,000,000,000 for a cure for cancer. The listing says it's going to be taken down in 60 days.");
        beat(in, "But when you check your bank account, you have $0.64. You hear the casino in town gives loans.");
    }

    private static void beat(Scanner in, String text) {
        System.out.println(text);
        System.out.print("(Enter to continue)");
        in.nextLine();
    }
}