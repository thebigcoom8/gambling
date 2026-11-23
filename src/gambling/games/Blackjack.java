package gambling.games;

import gambling.util.*;
import java.util.Scanner;

public class Blackjack {
    // Plays blackjack and returns the player's updated points.
    public static int play(Scanner in, int point) {
        System.out.println("Would you like to play blackjack?");
        String input = in.nextLine();

        while (Input.isYes(input)) {
            int bet = Betting.getBet(in, point, "Your score is " + point + " points, make a bet: ");

            if (bet != Betting.INVALID) {
                point -= bet;
                point += playHand(in, bet);
            }

            System.out.println("Would you like to play blackjack again?");
            input = in.nextLine();
            Card.shuffle();
        }
        return point;
    }

    // Plays one hand and returns the payout (0 on a loss).
    private static int playHand(Scanner in, int bet) {
        int dscore = 0;
        int dealerAces = 0;
        Card dfc = new Card();
        dscore += dfc.getValue();
        if (dfc.getValue() == 11) {
            dealerAces++;
        }
        System.out.println("Dealer: " + dfc);

        int userscore = 0;
        int userAces = 0;
        Card ufc = new Card();
        Card usc = new Card();
        userscore += ufc.getValue() + usc.getValue();
        if (ufc.getValue() == 11) {
            userAces++;
        }
        if (usc.getValue() == 11) {
            userAces++;
        }
        while (userscore > 21 && userAces > 0) {
            userscore -= 10;
            userAces--;
        }

        System.out.println("Your cards are:");
        System.out.println(ufc + " " + ufc.getCardEmoji());
        System.out.println(usc + " " + usc.getCardEmoji());
        System.out.println("Your value is: " + userscore + "\nDealer showing: " + dscore);

        Card dsc = new Card();
        dscore += dsc.getValue();
        if (dsc.getValue() == 11) {
            dealerAces++;
        }
        while (dscore > 21 && dealerAces > 0) {
            dscore -= 10;
            dealerAces--;
        }

        System.out.println("Hit or stand?");
        String input = in.nextLine();

        while (input.trim().toLowerCase().startsWith("h") && userscore < 21) {
            Card nyc = new Card();
            userscore += nyc.getValue();
            if (nyc.getValue() == 11) {
                userAces++;
            }
            while (userscore > 21 && userAces > 0) {
                userscore -= 10;
                userAces--;
            }

            System.out.println("You got: " + nyc);
            System.out.println("New value is: " + userscore);

            if (userscore >= 21) {
                break;
            }

            System.out.println("Hit or stand?");
            input = in.nextLine();
        }

        if (userscore > 21) {
            System.out.println("You went over 21, you bust!\nThe dealer had: " + dscore);
            return 0;
        }

        while (dscore < 17) {
            Card dealerHit = new Card();
            dscore += dealerHit.getValue();
            if (dealerHit.getValue() == 11) {
                dealerAces++;
            }
            while (dscore > 21 && dealerAces > 0) {
                dscore -= 10;
                dealerAces--;
            }
            System.out.println("Dealer drew: " + dealerHit);
        }

        if (dscore > 21) {
            System.out.println("Dealer busted with " + dscore + "! You win!");
            return bet * 3;
        } else if (userscore > dscore) {
            System.out.println("You win! Dealer had: " + dscore);
            return bet * 2;
        } else if (userscore < dscore) {
            System.out.println("You lose, dealer had: " + dscore);
            return 0;
        } else {
            System.out.println("It's a tie! Both had: " + dscore);
            return bet;
        }
    }
}
