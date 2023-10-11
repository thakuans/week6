/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blackjackgame;

/**
 *
 * @author 14379
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {
    public static void main(String[] args) {
        List<Card> deck = initializeDeck();
        shuffleDeck(deck);

        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Initial deal: each player gets two cards
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));

        boolean playerBust = false;
        boolean dealerBust = false;

        while (true) {
            System.out.println("in Hand " + playerHand);
            System.out.println(" opponent hand: " + dealerHand.get(0) + " and an different card");

            int playerScore = calculateHandValue(playerHand);
            int dealerScore = calculateHandValue(dealerHand);

            if (playerScore == 21) {
                System.out.println("hurray! black jack !you win");
                break;
            } else if (playerScore > 21) {
                System.out.println( " Sorry ! burst! You lose the game.");
                playerBust = true;
                break;
            }

            System.out.print("what do you want (h)for hit and (S) for stand? ");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("H")) {
                playerHand.add(deck.remove(0));
            } else if (choice.equals("S")) {
                while (dealerScore < 17) {
                    dealerHand.add(deck.remove(0));
                    dealerScore = calculateHandValue(dealerHand);
                }

                System.out.println("opponent Hand" + dealerHand);

                if (dealerScore > 21) {
                    System.out.println("opponent busts. You win! hurray!");
                } else if (dealerScore >= playerScore) {
                    System.out.println("Dealer wins. ! try again!");
                } else {
                    System.out.println("You win! Hurray!");
                }
                break;
            }
        }

        scanner.close();
    }

    public static List<Card> initializeDeck() {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};

        for (String suit : suits) {
            for (int value = 1; value <= 13; value++) {
                deck.add(new Card(suit, value));
            }
        }

        return deck;
    }

    public static void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    public static int calculateHandValue(List<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            int cardValue = Math.min(10, card.getValue());
            value += cardValue;

            if (card.getValue() == 1) {
                numAces++;
                value += 10; // Treat Ace as 11 unless it would cause a bust
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }
}
