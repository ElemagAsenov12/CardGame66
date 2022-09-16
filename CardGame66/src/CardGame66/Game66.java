package CardGame66;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class Game66 {
    public static int indexOfPlayerOnTurn = 0;
    private Stack<Card> deck;
    public static ArrayList<Player> players = new ArrayList<>();

    public Game66(Player player1, Player player2) {
        players.add(player1);
        players.add(player2);
        this.deck = new Stack<Card>();
    }

    private void buildDeck() {
        putAllClubs();
        putAllDiamonds();
        putAllHearts();
        putAllSpades();
    }

    private void putAllClubs() {
        int power = 2;

        for (int i = 0; i < Card.cardNames.length; i++) {

            if (i == 2) {
                power = 3;
            } else if (i == 3) {
                power = 4;
            } else if (i == 4) {
                power = 10;
            } else if (i == 5) {
                power = 11;
            }

            this.deck.add(new Card(TypeOfCard.CLUBS, Card.cardNames[i], power));
        }
        //Inserting all (CLUBS) cards
    }

    private void putAllDiamonds() {
        int power = 2;

        for (int i = 0; i < Card.cardNames.length; i++) {

            if (i == 2) {
                power = 3;
            } else if (i == 3) {
                power = 4;
            } else if (i == 4) {
                power = 10;
            } else if (i == 5) {
                power = 11;
            }

            this.deck.add(new Card(TypeOfCard.DIAMONDS, Card.cardNames[i], power));
        }
        //Inserting all (DIAMONDS) cards
    }

    private void putAllHearts() {
        int power = 2;

        for (int i = 0; i < Card.cardNames.length; i++) {

            if (i == 2) {
                power = 3;
            } else if (i == 3) {
                power = 4;
            } else if (i == 4) {
                power = 10;
            } else if (i == 5) {
                power = 11;
            }

            this.deck.add(new Card(TypeOfCard.HEARTS, Card.cardNames[i], power));
        }
        //Inserting all (DIAMONDS) cards
    }

    private void putAllSpades() {
        int power = 2;

        for (int i = 0; i < Card.cardNames.length; i++) {

            if (i == 2) {
                power = 3;
            } else if (i == 3) {
                power = 4;
            } else if (i == 4) {
                power = 10;
            } else if (i == 5) {
                power = 11;
            }

            this.deck.add(new Card(TypeOfCard.SPADES, Card.cardNames[i], power));
        }
        //Inserting all (DIAMONDS) cards
    }

    private void shuffleDeck() {
        //Shuffling the deck a couple of times.
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(this.deck);
        }
    }

    public void play() {
        boolean cardTalon = true;
        boolean playerHasEnoughPoints = false;
        Player playerOnTurn;
        Player currentlyPlayed;
        Card trumpCard;
        Card playedCard;

        buildDeck();

        while (true) {
            System.out.println(players.get(0) + " has " + players.get(0).getPoints() + " points.");
            System.out.println(players.get(1) + " has " + players.get(1).getPoints() + " points.");

            //Checking to see if any player has reached 11 game points and won the game.
            if (players.get(0).getPoints() == 11) {
                System.out.println(players.get(0) + " wins the game.");
                break;
            } else if (players.get(1).getPoints() == 11) {
                System.out.println(players.get(1) + " wins the game.");
                break;
            }

            shuffleDeck();

            for (Player p : players) {
                p.resetAnnouncedPoints();
            }

            indexOfPlayerOnTurn = handOut(indexOfPlayerOnTurn);

            trumpCard = this.deck.pop();
            System.out.println("Trump Card[> " + trumpCard.getNameOfCard() + " - " + trumpCard.getTypeOFCard() + " <]");

            playerOnTurn = players.get(indexOfPlayerOnTurn);

            while (players.get(0).getHand().size() != 0 && players.get(1).getHand().size() != 0) {

                if (this.deck.size() > 1 && playerOnTurn.getCardBucket().size() > 0) {
                    trumpCard = playerOnTurn.optionSwitchTrumps(trumpCard);
                }

                cardTalon = playerOnTurn.optionCloseTheTalon(cardTalon);
                playerHasEnoughPoints = playerOnTurn.optionEnoughPoints();

                if (playerHasEnoughPoints) {
                    break;
                }

                playedCard = playerOnTurn.playACard(cardTalon, trumpCard); // indexOfPlayerOnTurn changes in playACard().
                currentlyPlayed = playerOnTurn;

                playerOnTurn = players.get(indexOfPlayerOnTurn);
                playerOnTurn.playACard(cardTalon, trumpCard, playedCard, currentlyPlayed);

                playerOnTurn = players.get(indexOfPlayerOnTurn);

                if (cardTalon && this.deck.size() > 0) {
                    draw(playerOnTurn, trumpCard);
                }
            }

            if (playerHasEnoughPoints) {
                whoWins(playerOnTurn);
            } else {
                whoWins();
            }

            bringAllCardsTogether(trumpCard);
        }
    }

    private void whoWins(Player playerOnTurn) {
        Player p2;

        if (playerOnTurn.equals(players.get(0))) {
            p2 = players.get(1);
        } else {
            p2 = players.get(0);
        }

        if (playerOnTurn.countPoints() >= 66) {
            if (p2.countPoints() >= 33) {

                playerOnTurn.addPoints(1);
                indexOfPlayerOnTurn = players.indexOf(playerOnTurn);
                System.out.println(playerOnTurn + " wins the round and 1 game point.");

            } else if (p2.getCardBucket().size() > 0 && p2.countPoints() < 33) {

                playerOnTurn.addPoints(2);
                indexOfPlayerOnTurn = players.indexOf(playerOnTurn);
                System.out.println(playerOnTurn + " wins the round and 2 game points.");

            } else if (p2.getCardBucket().size() == 0) {

                playerOnTurn.addPoints(3);
                indexOfPlayerOnTurn = players.indexOf(playerOnTurn);
                System.out.println(playerOnTurn + " wins the round and 3 game points.");

            }
        } else {
            if (p2.getCardBucket().size() == 0) {

                p2.addPoints(3);
                indexOfPlayerOnTurn = players.indexOf(p2);
                System.out.println(p2 + " wins the round and 3 game points.");

            } else {

                p2.addPoints(2);
                indexOfPlayerOnTurn = players.indexOf(p2);
                System.out.println(p2 + " wins the round and 2 game points.");

            }
        }
    }

    private void whoWins() {
        int pointsP1 = players.get(0).countPoints();
        int pointsP2 = players.get(1).countPoints();

        if (pointsP1 > pointsP2) {
            if (pointsP2 >= 33) {

                players.get(0).addPoints(1);
                indexOfPlayerOnTurn = players.indexOf(players.get(0));
                System.out.println(players.get(0) + " wins the round and 1 game point.");

            } else if (pointsP2 < 33 && players.get(1).getCardBucket().size() > 0) {

                players.get(0).addPoints(2);
                indexOfPlayerOnTurn = players.indexOf(players.get(0));
                System.out.println(players.get(0) + " wins the round and 2  game points.");

            } else if (pointsP1 == pointsP2) {
                System.out.println("No one wins game points. The card points are equal.");
            } else {

                players.get(0).addPoints(3);
                indexOfPlayerOnTurn = players.indexOf(players.get(0));
                System.out.println(players.get(0) + " wins the round and 3 game points.");

            }
        } else {
            if (pointsP1 >= 33) {

                players.get(1).addPoints(1);
                indexOfPlayerOnTurn = players.indexOf(players.get(1));
                System.out.println(players.get(1) + " wins the round and 1 game point.");

            } else if (pointsP1 < 33 && players.get(0).getCardBucket().size() > 0) {

                players.get(1).addPoints(2);
                indexOfPlayerOnTurn = players.indexOf(players.get(1));
                System.out.println(players.get(1) + " wins the round and 2 game points.");

            } else if (pointsP1 == pointsP2) {
                System.out.println("No one wins game points. The card points are equal.");
            } else {

                players.get(1).addPoints(3);
                indexOfPlayerOnTurn = players.indexOf(players.get(1));
                System.out.println(players.get(0) + " wins the round and 3 game points.");

            }
        }
    }

    private void bringAllCardsTogether(Card trumpCard) {
        int bucketSize = players.get(0).getCardBucket().size();
        int handSize = players.get(0).getHand().size();

        for (int i = 0; i < bucketSize; i++) {
            this.deck.add(players.get(0).getCardBucket().remove(0));
        }

        for (int i = 0; i < handSize; i++) {
            this.deck.add(players.get(0).getHand().remove(0));
        }

        bucketSize = players.get(1).getCardBucket().size();
        handSize = players.get(0).getHand().size();

        for (int i = 0; i < bucketSize; i++) {
            this.deck.add(players.get(1).getCardBucket().remove(0));
        }

        for (int i = 0; i < handSize; i++) {
            this.deck.add(players.get(1).getHand().remove(0));
        }

        // >NOTE< using the "currentPlayer.getCardBucket().size()" expression in for loop doesn't work correctly.

//        for (Player currentPlayer : players) {
//            //Harvesting the cards from both players buckets.
//            for (int i = 0; i < currentPlayer.getCardBucket().size(); i++) {
//                this.deck.add(currentPlayer.getCardBucket().remove(0));
//            }
//
//            //Harvesting the cards from both players hands.
//            for (int i = 0; i < currentPlayer.getHand().size(); i++) {
//                this.deck.add(currentPlayer.getHand().remove(0));
//            }
//        }

        this.deck.add(trumpCard);
    }

    private void draw(Player playerOnTurn, Card trumpCard) {
        if (playerOnTurn.equals(players.get(0))) {
            if (this.deck.size() == 1) {
                playerOnTurn.drawCard(this.deck.pop());
                players.get(1).drawCard(trumpCard);
            } else {
                playerOnTurn.drawCard(this.deck.pop());
                players.get(1).drawCard(this.deck.pop());
            }
        } else {
            if (this.deck.size() == 1) {
                playerOnTurn.drawCard(this.deck.pop());
                players.get(0).drawCard(trumpCard);
            } else {
                playerOnTurn.drawCard(this.deck.pop());
                players.get(0).drawCard(this.deck.pop());
            }
        }
    }

    private int handOut(int handingOut) {
        //There are two players in the game(Game.players) with indexes 0 and 1;
        //Checking which player is handing out to get the hanging order correctly.
        if (handingOut == 0) {
            // first 3 cards for p2
            for (int i = 0; i < 3; i++) {
                this.players.get(1).drawCard(this.deck.pop());
            }
            //first 3 cards for p1
            for (int i = 0; i < 3; i++) {
                this.players.get(0).drawCard(this.deck.pop());
            }
            //second round of cards p2
            for (int i = 0; i < 3; i++) {
                this.players.get(1).drawCard(this.deck.pop());
            }
            // p1
            for (int i = 0; i < 3; i++) {
                this.players.get(0).drawCard(this.deck.pop());
            }
            //returning the next player who has to play after the handing out(playerOnTurn).
            return 1;
        } else {
            //At this point the second player is handing.
            // first 3 cards for p1
            for (int i = 0; i < 3; i++) {
                this.players.get(1).drawCard(this.deck.pop());
            }
            //first 3 cards for p2
            for (int i = 0; i < 3; i++) {
                this.players.get(0).drawCard(this.deck.pop());
            }
            //second round of cards for p1
            for (int i = 0; i < 3; i++) {
                this.players.get(1).drawCard(this.deck.pop());
            }
            // for p2
            for (int i = 0; i < 3; i++) {
                this.players.get(0).drawCard(this.deck.pop());
            }
            //returning the next player who has to play after the handing out(playerOnTurn).
            return 0;
        }
    }
}
