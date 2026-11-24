package gambling;

import java.util.Scanner;

public class Story {
    public static void play(Scanner userin, int count, boolean win) {
        switch (count) {
            case 1: intro(userin); break;
            case 5:
            case 60: endgame(userin, count, win); break;
            default: System.out.print("unknown story"); break;
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

    private static void endgame(Scanner in, int count, boolean win) {
        if (win && count == 60) {
            beat(in, "the cure arrives just in time");
            beat(in, "Doctor: we have the cure. Your mother is going to live.");
        }
        if (!win && count == 60) {
            beat(in, "on day 60 you check the listing, it's gone");
            beat(in, "Mother: son, I don't have much longer, the doctor told me. You were looking for a cure");
            beat(in, "....");
            beat(in, "did you find one");
            beat(in, "I see");
            beat(in, "...");
            beat(in, "...");
            beat(in, "knock knock");
            beat(in, "Doctor: we got a message from someone, they said in five days a package is coming for you ma'am...");
            beat(in, "they said it would improve your condition slightly");
            beat(in, "it's not a cure but it'll buy you some time");
        }
        if (!win && count == 5) {
            beat(in, "you lost all my money, you'll pay for this");
            beat(in, "you're going to work at the casino for the rest of your life");
            beat(in, "you'll never see your mother again");
        }
    }
}