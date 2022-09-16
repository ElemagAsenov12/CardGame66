package CardGame66;

import java.util.Scanner;

public class UserPlayer extends Player {
    private static Scanner cin = new Scanner(System.in);

    public UserPlayer() {
        super();
    }

    private void printHand() {
        System.out.print("\n---------Current Hand------------");
        for (int i = 0; i < this.getHand().size(); i++) {
            System.out.print("\n Card[" + (i + 1) + "]:        " + this.getHand().get(i).getNameOfCard() + " - " + this.getHand().get(i).getTypeOFCard());
        }
        System.out.println("\n---------------------------------");
    }
    public boolean optionCloseTheTalon(boolean cardTalon) {
        char option;

        if (cardTalon == false) {
            //If the talon is already closed, we can't do anything.
            return false;
        } else if (cardTalon == true && this.getCardBucket().size() > 0) {
            //If the talon is open and there is a hand taken by the player:
            System.out.println("Do you want to close the talon?(y/n)");
            option = cin.next().charAt(0);

            if (option == 'y') {
                System.out.println("The talon is closed.");
                return false;
            } else {
                return true;
            }
        } else {
            //At this point the talon is open but the player haven't won a hand.
            return true;
        }
    }
    public boolean optionEnoughPoints() {
        char option;

        if (this.countPoints() > 40) {
            System.out.println("Do you think you have enough points to call the game? (y/n)");
            option = cin.next().charAt(0);

            switch (option) {
                case 'y':
                    return true;
                case 'n':
                    return false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
    public Card optionSwitchTrumps(Card trumpCard) {
        char option;
        Card newTrumpCard;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == trumpCard.getTypeOFCard() && currentCard.getNameOfCard().equals("9")) {
                System.out.println("Do you want to swap trumps? (y/n)");
                option = cin.next().charAt(0);

                switch (option) {
                    case 'y':
                        newTrumpCard = this.getHand().get(this.getHand().indexOf(currentCard));
                        this.getHand().remove(currentCard);
                        this.getHand().add(trumpCard);

                        System.out.println(this + " swapped 9 - " + trumpCard.getTypeOFCard() +
                                " with " + trumpCard.getNameOfCard() + " - " + trumpCard.getTypeOFCard());

                        return newTrumpCard;
                    default:
                        break;
                }
            }
        }

        return trumpCard;
    }
    public Card playACard(boolean cardTalon, Card trumpCard) {
        int index = 0;

        System.out.println("Trump Card[> " + trumpCard.getNameOfCard() + " - " + trumpCard.getTypeOFCard() + " <]");
        System.out.print("\nPlayer's turn!");
        printHand();
        Card playedCard;


        while (true) { //Forcing the user to give a correct input.
            System.out.println("Choose the corresponding number to a card to play it(1, 2, 3, etc): ");
            index = cin.nextInt() - 1;

            if (index < 1 || index > this.getHand().size() - 1) {
                System.out.println("You can choose a card between 1-" + this.getHand().size());
            } else {
                break;
            }
        }

        playedCard = this.getHand().get(index);
        //Saving the card into variable playedCard.

        this.getHand().remove(this.getHand().get(index));
        //Removing the card from the hand of the player.

        System.out.println(this + " played " + playedCard.getNameOfCard() + " - " + playedCard.getTypeOFCard());

        //If the played card is a Queen or a King, check to see if the player has an announcement to call.
        if (playedCard.getNameOfCard().equals("Q") || playedCard.getNameOfCard().equals("K")) {
            if (checkAnnouncement(playedCard)) {
                callAnnouncement(playedCard, trumpCard);
            }
        }

        //Updating the index of playerOnTurn, so we can get the other player at target afterwards.
        if (Game66.indexOfPlayerOnTurn == 0) {
            Game66.indexOfPlayerOnTurn = 1;
        } else {
            Game66.indexOfPlayerOnTurn = 0;
        }

        return playedCard;
    }
    public void playACard(boolean cardTalon, Card trumpCard, Card playedCard, Player currentlyPlayed) {
        int index = 0;
        System.out.println("Trump Card[> " + trumpCard.getNameOfCard() + " - " + trumpCard.getTypeOFCard() + " <]");
        System.out.print("\nPlayer's turn!");
        printHand();

        //First checking to see if the talon is still open for draws.
        if (cardTalon) {
            //If it's open then the current player can play whatever card he/she wants

            while (true) { //Forcing the user to give a correct index.
                System.out.println("Choose the corresponding number to a card to play it(1, 2, 3, etc): ");
                index = cin.nextInt() - 1;

                if (index < 1 || index > this.getHand().size() - 1) {
                    System.out.println("You can choose a card between 1-" + this.getHand().size());
                } else {
                    break;
                }
            }

            Card chosenCard = this.getHand().get(index);
            this.getHand().remove(chosenCard);

            System.out.println(this + " played " + chosenCard.getNameOfCard() + " - " + chosenCard.getTypeOFCard());

            //Comparing the cards.
            if (chosenCard.compareCards(playedCard, trumpCard)) {
                this.addCardToBucket(chosenCard);
                this.getHand().remove(chosenCard);

                this.addCardToBucket(playedCard);
                currentlyPlayed.getHand().remove(playedCard);
                System.out.println(this + " takes.");

                Game66.indexOfPlayerOnTurn = Game66.players.indexOf(this);
                //Initializing index of playerOnTurn for the draw(); afterwards and the starting player in the next turn.
            } else {
                currentlyPlayed.addCardToBucket(chosenCard);
                this.getHand().remove(chosenCard);

                currentlyPlayed.addCardToBucket(playedCard);
                currentlyPlayed.getHand().remove(playedCard);
                System.out.println(currentlyPlayed + " takes.");

                Game66.indexOfPlayerOnTurn = Game66.players.indexOf(currentlyPlayed);
            }
        } else {
            //The talon is closed. Strict rules on.
            while (true) {

                while (true) { //Forcing the user to give a correct index.
                    System.out.println("Choose the corresponding number to a card to play it(1, 2, 3, etc): ");
                    index = cin.nextInt() - 1;

                    if (index < 1 || index > this.getHand().size() - 1) {
                        System.out.println("You can choose a card between 1-" + this.getHand().size());
                    } else {
                        break;
                    }
                }

                Card chosenCard = this.getHand().get(index);

                if (checkForPaint(playedCard)) {
                    if (chosenCard.getTypeOFCard() == playedCard.getTypeOFCard()) {
                        if (chosenCard.compareCards(playedCard, trumpCard)) {
                            this.addCardToBucket(chosenCard);
                            this.getHand().remove(chosenCard);

                            this.addCardToBucket(playedCard);
                            currentlyPlayed.getHand().remove(playedCard);
                            System.out.println(this + " takes.");

                            Game66.indexOfPlayerOnTurn = Game66.players.indexOf(this);
                            break;

                        } else {
                            currentlyPlayed.addCardToBucket(chosenCard);
                            this.getHand().remove(chosenCard);

                            currentlyPlayed.addCardToBucket(playedCard);
                            currentlyPlayed.getHand().remove(playedCard);
                            System.out.println(currentlyPlayed + " takes.");

                            Game66.indexOfPlayerOnTurn = Game66.players.indexOf(currentlyPlayed);
                            break;
                        }
                    } else {
                        System.out.println("You must play a " + playedCard.getTypeOFCard() + " card.");
                    }
                } else if (!checkForPaint(playedCard) && checkForTrumps(trumpCard)) {
                    if (chosenCard.getTypeOFCard() == trumpCard.getTypeOFCard()) {
                        this.addCardToBucket(chosenCard);
                        this.getHand().remove(chosenCard);

                        this.addCardToBucket(playedCard);
                        currentlyPlayed.getHand().remove(playedCard);
                        System.out.println(this + " takes.");

                        Game66.indexOfPlayerOnTurn = Game66.players.indexOf(this);
                        break;
                    } else {
                        System.out.println("You must play a trump!");
                    }
                } else if (!checkForPaint(playedCard) && !checkForTrumps(trumpCard)) {
                    currentlyPlayed.addCardToBucket(chosenCard);
                    this.getHand().remove(chosenCard);

                    currentlyPlayed.addCardToBucket(playedCard);
                    currentlyPlayed.getHand().remove(playedCard);
                    System.out.println(currentlyPlayed + " takes.");

                    Game66.indexOfPlayerOnTurn = Game66.players.indexOf(currentlyPlayed);
                    break;
                }
            }
        }
    }
}
