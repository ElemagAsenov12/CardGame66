package CardGame66;

import java.util.ArrayList;


public class Player {
    private ArrayList<Card> hand;
    private int points;
    private int announcedRoundPoints;
    private ArrayList<Card> cardBucket; //Queue might work better.

    public void resetAnnouncedPoints(){
        this.announcedRoundPoints = 0;
    }
    public ArrayList<Card> getCardBucket(){
        return this.cardBucket;
    }
    public void addCardToBucket(Card card) {
        this.cardBucket.add(card);
    }
    public Player() {
        this.hand = new ArrayList<Card>();
        this.points = 0;
        this.announcedRoundPoints = 0;
        this.cardBucket = new ArrayList<Card>();
    }
    public ArrayList<Card> getHand() {
        return this.hand;
    }
    public int countPoints(){
        int countOfPoints = this.announcedRoundPoints;
        for(Card currentCard : this.cardBucket){
            countOfPoints += currentCard.getPowerOfCard();
        }
        return countOfPoints;
    }
    public int getPoints() {
        return this.points;
    }
    public void addPoints(int points) {
        this.points += points;
    }
    public void drawCard(Card card){
        this.getHand().add(card);
    }
    public boolean optionCloseTheTalon(boolean cardTalon){
        if(this instanceof ComputerPlayer){
            return ((ComputerPlayer)this).optionCloseTheTalon(cardTalon);
        } else {
            return ((UserPlayer)this).optionCloseTheTalon(cardTalon);
        }
    }
    public Card playACard(boolean cardTalon, Card trumpCard){
        if(this instanceof ComputerPlayer){
            return ((ComputerPlayer)this).playACard(cardTalon, trumpCard);
        } else {
            return ((UserPlayer)this).playACard(cardTalon, trumpCard);
        }
    }
    public void playACard(boolean cardTalon, Card trumpCard, Card playedCard, Player currentlyPlayed){
        if(this instanceof ComputerPlayer){
            ((ComputerPlayer)this).playACard(cardTalon, trumpCard, playedCard, currentlyPlayed);
        } else {
            ((UserPlayer)this).playACard(cardTalon, trumpCard, playedCard, currentlyPlayed);
        }
    }
    protected boolean checkAnnouncement(Card playedCard){
        String lookingFor = null;

        if (playedCard.getNameOfCard().equals("Q")) {
            lookingFor = "K";
        } else {
            lookingFor = "Q";
        }

        for(Card currentCard : this.getHand()){
            if(currentCard.getTypeOFCard() == playedCard.getTypeOFCard() && currentCard.getNameOfCard().equals(lookingFor)){
                return true;
            }
        }
        return false;
    }
    protected boolean checkForTrumps(Card trumpCard) {
        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == trumpCard.getTypeOFCard()) {
                return true;
            }
        }
        return false;
    }
    protected boolean checkForPaint(Card card) {
        for (Card currentCard : this.getHand()) {
            if (currentCard.getTypeOFCard() == card.getTypeOFCard()) {
                return true;
            }
        }
        return false;
    }
    protected void callAnnouncement(Card playedCard, Card trumpCard){
        if(playedCard.getTypeOFCard() == trumpCard.getTypeOFCard()){
            this.addPoints(40);
            this.announcedRoundPoints = 40;
            System.out.println(this + " announced 40!");
        } else {
            this.addPoints(20);
            this.announcedRoundPoints = 20;
            System.out.println(this + " announced 20!");
        }
    }
    public boolean optionEnoughPoints(){
       if(this instanceof UserPlayer){
           return ((UserPlayer) this).optionEnoughPoints();
       } else {
           return ((ComputerPlayer) this).optionEnoughPoints();
       }
    }
    public Card optionSwitchTrumps(Card trumpCard){
        if(this instanceof UserPlayer){
            return ((UserPlayer) this).optionSwitchTrumps(trumpCard);
        } else {
            return ((ComputerPlayer) this).optionSwitchTrumps(trumpCard);
        }
    }

}
