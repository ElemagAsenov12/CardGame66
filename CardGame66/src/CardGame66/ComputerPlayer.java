package CardGame66;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
    public ComputerPlayer() {
        super();
    }

    public Card playACard(boolean cardTalon, Card trumpCard) {
        System.out.println("\nComputer's turn!");
        Card playedCard;

        playedCard = whichCardDoIPlay(trumpCard);
        //Saving the card into variable playedCard and removing the card from the computer's hand.

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
    private int countPointsInHand() { //
        int points = 0;
        for (Card currentCard : this.getHand()) {
            points += currentCard.getPowerOfCard();
        }
        return points;
    }
    public boolean optionCloseTheTalon(boolean cardTalon) {

        if (cardTalon == false) {
            //If the talon is already closed, we can't do anything.
            return false;
        } else if (cardTalon == true && this.getCardBucket().size() > 0) {
            if (this.getPoints() > 33 && countPointsInHand() > 30) {
                System.out.println("Computer closed the talon!");
                return false;
            }
        } else {
            //At this point the talon is open but there are no won hands by the computer.
            //Should be reachable only if it's the first hand of the current game, and it is computer's turn.
            return true;
        }

        return true;
    }
    public Card optionSwitchTrumps(Card trumpCard) {
        Card newTrumpCard;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == trumpCard.getTypeOFCard() && currentCard.getNameOfCard().equals("9")) {

                newTrumpCard = this.getHand().get(this.getHand().indexOf(currentCard));
                this.getHand().remove(currentCard);
                this.getHand().add(trumpCard);

                System.out.println(this + " swapped 9 - " + trumpCard.getTypeOFCard() +
                        " with " + trumpCard.getNameOfCard() + " - " + trumpCard.getTypeOFCard());

                return newTrumpCard;

            }
        }

        return trumpCard;
    }
    public boolean optionEnoughPoints(){
        if(this.countPoints() > 65){
            return true;
        } else {
            return false;
        }
    }
    public Card cardToTakeWith(Card playedCard) {
        ArrayList<Card> options = new ArrayList<>();
        Card strongestOption;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == playedCard.getTypeOFCard()) {
                options.add(currentCard);
            }
        }

        strongestOption = options.get(0);

        for (Card currentCard : options) {
            if (currentCard.getPowerOfCard() > strongestOption.getPowerOfCard()) {
                strongestOption = currentCard;
            }
        }

        this.getHand().remove(strongestOption);
        return strongestOption;
    }
    public void playACard(boolean cardTalon, Card trumpCard, Card playedCard, Player currentlyPlayed) {
        System.out.println("\nComputer's turn!");
        Card cardToBePlayed = null;

        //First checking to see if the talon is still open for draws.
        if (cardTalon) {
            //If it's open then the bot can play whatever card it wants
            if (checkForPaint(playedCard)) {

                if (doIHaveStrongerCard(playedCard)) {

                    cardToBePlayed = cardToTakeWith(playedCard);

                } else {
                    if (checkForTrumps(trumpCard)) {

                        if (playedCard.getPowerOfCard() > 5) {

                            cardToBePlayed = playATrump(trumpCard);

                        } else {

                            cardToBePlayed = playTheWeakestOf(playedCard);

                        }
                    } else {
                        cardToBePlayed = whichCardDoIPlay(trumpCard);
                    }
                }
            } else {
                if(checkForTrumps(trumpCard)){

                    if(playedCard.getPowerOfCard() > 5){

                        cardToBePlayed = playATrump(trumpCard);

                    }

                } else {

                    cardToBePlayed = whichCardDoIPlay(trumpCard);

                }
            }
        } else { // Talon is closed. Strict rules are on.
            if (checkForPaint(playedCard)) {

                if (doIHaveStrongerCard(playedCard)) {

                    cardToBePlayed = cardToTakeWith(playedCard);

                } else {

                    cardToBePlayed = playTheWeakestOf(playedCard);

                }
            } else { //The bot doesn't have a card from the same paint as the played card.
                if (checkForTrumps(trumpCard)) {

                    cardToBePlayed = playATrump(trumpCard);

                } else {

                    cardToBePlayed = whichCardDoIPlay(trumpCard);

                }
            }
        }

        System.out.println(this + " played " + cardToBePlayed.getNameOfCard() + " - " + cardToBePlayed.getTypeOFCard());

        //Comparing the cards:
        if (cardToBePlayed.compareCards(playedCard, trumpCard)) {
            this.addCardToBucket(cardToBePlayed);
            this.getHand().remove(cardToBePlayed);

            this.addCardToBucket(playedCard);
            currentlyPlayed.getHand().remove(playedCard);
            System.out.println(this + " takes.");

            Game66.indexOfPlayerOnTurn = Game66.players.indexOf(this);
            //Initializing index of playerOnTurn for the draw(); afterwards and the starting player in the next turn.
        } else {
            currentlyPlayed.addCardToBucket(cardToBePlayed);
            this.getHand().remove(cardToBePlayed);

            currentlyPlayed.addCardToBucket(playedCard);
            currentlyPlayed.getHand().remove(playedCard);
            System.out.println(currentlyPlayed + " takes.");

            Game66.indexOfPlayerOnTurn = Game66.players.indexOf(currentlyPlayed);
        }
    }
    private Card whichCardDoIPlay(Card trumpCard) { // playTheWeakestExceptTrumps();
        ArrayList<Card> choice = new ArrayList<>();
        Card weakestOption;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() != trumpCard.getTypeOFCard()) {
                choice.add(currentCard);
            }
        }

        if (choice.size() == 0) {
            //At this point the bot has only trumps. So it plays the weakest.
            return playTheWeakestOf(trumpCard);
        } else {

            weakestOption = choice.get(0);

            for (Card currentCard : this.getHand()) {
                if (weakestOption.getPowerOfCard() > currentCard.getPowerOfCard()) {
                    weakestOption = currentCard;
                }
            }
        }

        this.getHand().remove(weakestOption);
        return weakestOption;
    }
    private Card playTheWeakestOf(Card typeOfCard) {
        ArrayList<Card> choice = new ArrayList<>();
        Card weakestOption;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == typeOfCard.getTypeOFCard()) {
                choice.add(currentCard);
            }
        }

        weakestOption = choice.get(0);

        for (Card currentCard : this.getHand()) {
            if (currentCard.getPowerOfCard() < weakestOption.getPowerOfCard()) {
                weakestOption = currentCard;
            }
        }

        return weakestOption;
    }
    private boolean doIHaveStrongerCard(Card playedCard) {
        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == playedCard.getTypeOFCard() && currentCard.getPowerOfCard() > playedCard.getPowerOfCard()) {
                return true;
            }
        }
        return false;
    }
    private Card playATrump(Card trumpCard) {
        ArrayList<Card> choice = new ArrayList<>();
        Card weakestOption;

        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == trumpCard.getTypeOFCard()) {
                choice.add(currentCard);
            }
        }

        weakestOption = choice.get(0);

        for (Card currentCard : this.getHand()) {
            if (currentCard.getPowerOfCard() < weakestOption.getPowerOfCard()) {
                weakestOption = currentCard;
            }
        }

        this.getHand().remove(weakestOption);
        return weakestOption;
    }
}
