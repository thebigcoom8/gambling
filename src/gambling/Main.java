package gambling;

import gambling.games.*;
import gambling.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userin = new Scanner(System.in);
        int point = 0;
        String input = "yes";
        int count = 1; // Starting at Day 1 makes Day 60 exact
        int amount = 0;
        int debt = 0;

        Story.play(userin, count);

        // game loop
        while (Input.isYes(input)) {

            // get a loan
            if (point <= 0 && debt < 1500) {
                System.out.print("you're broke haha, but I'll give you a loan. How much do you want to borrow? you have: " + point + " points \n(max remaining loan " + (1500 - debt) + ") loan amount: ");
                amount = userin.nextInt();
                userin.nextLine(); // clear buffer

                if (amount <= 0) {
                    System.out.println("you need points to play... I'll give you 100.");
                    amount = Math.min(100, 1500 - debt);
                } else if (amount + debt > 1500) {
                    System.out.println("you're greedy... I'll give what I can.");
                    amount = 1500 - debt;
                }
                point += amount;
                debt += amount;
            }

            System.out.println("what game would you like to play?\n(0 = random number game)\n(1 = black jack)\n(2 = slots)\n(3 = roulette)\n(4 = craps)\n(5 = CoinFlip)\n(-1 = random)");
            int game = userin.nextInt();
            userin.nextLine();

            // random game
            if (game == -1) {
                game = (int) (6 * Math.random());
            }

            // pick a game
            switch (game) {
                case 0: point = NumberGame.play(userin, point, count == 1); break;
                case 1: point = Blackjack.play(userin, point); break;
                case 2: point = Slots.play(userin, point); break;
                case 3: point = Roulette.play(userin, point); break;
                case 4: point = Craps.play(userin, point); break;
                case 5: point = CoinFlip.play(userin, point); break;
                default: System.out.println("That's not a game!"); break;
            }

            // payback loan every 5 days
            if (debt > 0 && count % 5 == 0) {
                int payment = Math.min(debt, Math.max(1, debt / 10));
                System.out.println("you have to pay back part of your loan now... hope you made some money");
                point -= payment;
                debt -= payment;
                System.out.println("you paid: " + payment + " points, you still owe: " + debt);
            }

            // tip jar
            if (point > 0) {
                int tip = Betting.getBet(userin, point, "please take this time to use the tip jar, you have: " + point + " points\n");
                if (tip != Betting.INVALID) {
                    point -= tip;
                }
            }

            // win, loss, and new day logic
            if (count == 60) {
                System.out.println("it's day 60, you have: " + point + " points");

                if (point >= 1000000000) {
                    System.out.println("you made enough you can afford the payment, good job!");
                    point -= 1000000000;
                    break;
                } else {
                    System.out.println("you don't have enough, your mother is going to die. you had: " + point + ", you needed: " + (1000000000 - point) + " more points.");
                    break;
                }
            } else if (debt >= 1500 && point <= 0) {
                System.out.println("you lost, on day: " + count + ", your mother will die now.");
                break;
            } else {
                System.out.println("day: " + count + " of 60. would you like to play new day?");
                input = userin.nextLine();
                count++;
            }
        }
    }
}
