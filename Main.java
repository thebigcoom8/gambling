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
        int loan = 0;

        // game loop
        while (Input.isYes(input)) {
                if (point <= 0) {
                    System.out.print("your broke haha, but just for you ill give you a loan how much do you want to borrow? you have: " + point + " points (max loan 1500) \nloan amount:");
                    loan = userin.nextInt();
                    if (loan <= 1500) {
                        point += loan;
                    } else {
                        System.out.println("your greedy... ill give 1,500");
                        loan = 1500;
                        point += loan;
                    }
            
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

            if (loan > 0){
                System.out.println("you have to pay back your loan now... hope you made some money");
                point -= loan;
                System.out.println("you payed: " + loan + " points");
                loan = 0;
        }


            // tip jar
            int tip = Betting.getBet(userin, point, "please take this time to use the tip jar, you have: " + point + " points\n");
            if (tip != Betting.INVALID) {
                point -= tip;
            }

            // restart or end
            System.out.println("would you like to play new game");
            input = userin.nextLine();
            count++;
        }
    }
}