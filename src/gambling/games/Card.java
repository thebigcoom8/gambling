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
        public String getCardEmoji() {
        return switch (suit + "-" + number) {
            // 1 = Hearts
            case "1-1" -> "\uD83C\uDCB1";  // 🂱
            case "1-2" -> "\uD83C\uDCB2";  // 🂲
            case "1-3" -> "\uD83C\uDCB3";  // 🂳
            case "1-4" -> "\uD83C\uDCB4";  // 🂴
            case "1-5" -> "\uD83C\uDCB5";  // 🂵
            case "1-6" -> "\uD83C\uDCB6";  // 🂶
            case "1-7" -> "\uD83C\uDCB7";  // 🂷
            case "1-8" -> "\uD83C\uDCB8";  // 🂸
            case "1-9" -> "\uD83C\uDCB9";  // 🂹
            case "1-10" -> "\uD83C\uDCBA"; // 🂺
            case "1-11" -> "\uD83C\uDCBB"; // 🂻
            case "1-12" -> "\uD83C\uDCBD"; // 🂽
            case "1-13" -> "\uD83C\uDCBE"; // 🂾

            // 2 = Diamonds
            case "2-1" -> "\uD83C\uDCC1";  // 🃁
            case "2-2" -> "\uD83C\uDCC2";  // 🃂
            case "2-3" -> "\uD83C\uDCC3";  // 🃃
            case "2-4" -> "\uD83C\uDCC4";  // 🃄
            case "2-5" -> "\uD83C\uDCC5";  // 🃅
            case "2-6" -> "\uD83C\uDCC6";  // 🃆
            case "2-7" -> "\uD83C\uDCC7";  // 🃇
            case "2-8" -> "\uD83C\uDCC8";  // 🃈
            case "2-9" -> "\uD83C\uDCC9";  // 🃉
            case "2-10" -> "\uD83C\uDCCA"; // 🃊
            case "2-11" -> "\uD83C\uDCCB"; // 🃋
            case "2-12" -> "\uD83C\uDCCD"; // 🃍
            case "2-13" -> "\uD83C\uDCCE"; // 🃎

            // 3 = Clubs
            case "3-1" -> "\uD83C\uDCD1";  // 🃑
            case "3-2" -> "\uD83C\uDCD2";  // 🃒
            case "3-3" -> "\uD83C\uDCD3";  // 🃓
            case "3-4" -> "\uD83C\uDCD4";  // 🃔
            case "3-5" -> "\uD83C\uDCD5";  // 🃕
            case "3-6" -> "\uD83C\uDCD6";  // 🃖
            case "3-7" -> "\uD83C\uDCD7";  // 🃗
            case "3-8" -> "\uD83C\uDCD8";  // 🃘
            case "3-9" -> "\uD83C\uDCD9";  // 🃙
            case "3-10" -> "\uD83C\uDCDA"; // 🃚
            case "3-11" -> "\uD83C\uDCDB"; // 🃛
            case "3-12" -> "\uD83C\uDCDD"; // 🃝
            case "3-13" -> "\uD83C\uDCDE"; // 🃞

            // 4 = Spades
            case "4-1" -> "\uD83C\uDCA1";  // 🂡
            case "4-2" -> "\uD83C\uDCA2";  // 🂢
            case "4-3" -> "\uD83C\uDCA3";  // 🂣
            case "4-4" -> "\uD83C\uDCA4";  // 🂤
            case "4-5" -> "\uD83C\uDCA5";  // 🂥
            case "4-6" -> "\uD83C\uDCA6";  // 🂦
            case "4-7" -> "\uD83C\uDCA7";  // 🂧
            case "4-8" -> "\uD83C\uDCA8";  // 🂨
            case "4-9" -> "\uD83C\uDCA9";  // 🂩
            case "4-10" -> "\uD83C\uDCAA"; // 🂪
            case "4-11" -> "\uD83C\uDCAB"; // 🂫
            case "4-12" -> "\uD83C\uDCAD"; // 🂭
            case "4-13" -> "\uD83C\uDCAE"; // 🂮

            default -> "\uD83C\uDCCF";     // 🃏
        };
    }

}

