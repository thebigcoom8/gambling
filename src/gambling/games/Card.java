package gambling.games;

import gambling.util.*;
public class Card {
    private static boolean[][] played = new boolean[5][14];
    private static int cardsDrawn = 0;

    int suit;   // 1 = Hearts, 2 = Diamonds, 3 = Clubs, 4 = Spades
    int number; // 1 = Ace, 2-10 = number cards, 11 = Jack, 12 = Queen, 13 = King

    public Card() {
        if (cardsDrawn >= 52) {
            shuffle();
        }

        do {
            suit = (int) (Math.random() * 4) + 1;
            number = (int) (Math.random() * 13) + 1;
        } while (played[suit][number]);

        played[suit][number] = true;
        cardsDrawn++;
    }

    public static void shuffle() {
        played = new boolean[5][14];
        cardsDrawn = 0;
    }

    public String toString() {
        String suitName = "";
        switch (suit) {
            case 1: suitName = "Hearts"; break;
            case 2: suitName = "Diamonds"; break;
            case 3: suitName = "Clubs"; break;
            case 4: suitName = "Spades"; break;
        }

        String valueName = "";
        switch (number) {
            case 1:  valueName = "Ace"; break;
            case 11: valueName = "Jack"; break;
            case 12: valueName = "Queen"; break;
            case 13: valueName = "King"; break;
            default: valueName = String.valueOf(number); break;
        }

        return valueName + " of " + suitName;
    }

    public int getValue() {
        if (number >= 11) {
            return 10;
        } else if (number == 1) {
            return 11;
        } else {
            return number;
        }
    }
}
