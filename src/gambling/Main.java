package gambling;

import gambling.games.*;
import gambling.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userin = new Scanner(System.in);
        int point = 0;
        String input = "yes";
        int count = 0;
        int amount = 0;
        int debt = 0;
        

        Story.play(userin);

        // game loop
        while (Input.isYes(input)) {
                if (point <= 0) {
                    System.out.print("your broke haha, but I'll give you a loan how much do you want to borrow? you have: " + point + " points \n(max loan 1500) loan amount:");
                    amount = userin.nextInt();
                    if (amount + debt > 1500) {
                        System.out.println("your greedy... ill give what I can");
                        amount = 1500 - debt;
                    }
                    point += amount;
                    debt += amount;
                }


            System.out.println("what game would you like to play?\n(0 = random number game)\n(1 = black jack)\n(2 = slots)\n(3 = roulette)\n(4 = crap)\n(5 = CoinFlip)\n(-1 = random)");
            int game = userin.nextInt();
            userin.nextLine();

            // random game (number must be the total number of games, not the highest game number)
            if (game == -1) {
                game = (int) (6* Math.random());
            }

            switch (game) {
                case 0: point = NumberGame.play(userin, point, count == 0); break;
                case 1: point = Blackjack.play(userin, point); break;
                case 2: point = Slots.play(userin, point); break;
                case 3: point = Roulette.play(userin, point); break;
                case 4: point = Craps.play(userin, point); break;
                case 5: point = CoinFlip.play(userin, point); break;
                default: System.out.println("That's not a game!"); break;
            }

            if (debt > 0 && (count + 1) % 5 == 0) {
                int payment = Math.min(debt, Math.max(1, debt / 10));
                System.out.println("you have to pay back part of your loan now... hope you made some money");
                point -= payment;
                debt -= payment;
                System.out.println("you paid: " + payment + " points, you still owe: " + debt);
            }


            // tip jar
            int tip = Betting.getBet(userin, point, "please take this time to use the tip jar, you have: " + point + " points\n");
            if (tip != Betting.INVALID) {
                point -= tip;
            }

            if (count == 60) {
                System.out.println("its day 60 you have: " + point + " points");
                if (point >= 1000000000){
                    System.out.println("you made enough you can afford the payment good job!");
                    point -= 1000000000;
                }
            }
            

            // restart or end
            System.out.println("day: " + count + "would you like to play new day");
            input = userin.nextLine();
            count++;
        }
    }
}
