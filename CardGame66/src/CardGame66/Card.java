package CardGame66;

public class Card {
    public static final String[] cardNames = {"9", "J", "Q", "K", "10", "A"};
    private TypeOfCard typeOFCard;
    private String nameOfCard;
    private int powerOfCard;

    public Card(TypeOfCard typeOFCard, String nameOfCard, int powerOfCard) {
        this.typeOFCard = typeOFCard;
        this.nameOfCard = nameOfCard;
        this.powerOfCard = powerOfCard;
    }
    public TypeOfCard getTypeOFCard() {
        return this.typeOFCard;
    }
    public String getNameOfCard() {
        return this.nameOfCard;
    }
    public void setNameOfCard(String nameOfCard) {
        this.nameOfCard = nameOfCard;
    }
    public int getPowerOfCard() {
        return this.powerOfCard;
    }
    public boolean compareCards(Card comparingCard, Card trumpCard) {
        //If the current card is from trump's paint
        if (this.getTypeOFCard() == trumpCard.getTypeOFCard() && comparingCard.getTypeOFCard() != trumpCard.getTypeOFCard()) {
            return true;
        } else if (this.getTypeOFCard() == trumpCard.getTypeOFCard() && comparingCard.getTypeOFCard() == trumpCard.getTypeOFCard()) {

            //In case we play 9 and J from the same paint, the program wouldn't know which is stronger since they both
            //count for 2 points. Solution:
            if (this.getNameOfCard().equals("9") && comparingCard.getNameOfCard().equals("J")) {
                return false;
            } else if (this.getNameOfCard().equals("J") && comparingCard.getNameOfCard().equals("9")) {
                return true;
            }

            if (this.getPowerOfCard() > comparingCard.getPowerOfCard()) {
                return true;
            } else {
                return false;
            }

            //If both cards are not trumps
        } else if (this.getTypeOFCard() == comparingCard.getTypeOFCard()) {

            if (this.getNameOfCard().equals("9") && comparingCard.getNameOfCard().equals("J")) {
                return false;
            } else if (this.getNameOfCard().equals("J") && comparingCard.getNameOfCard().equals("9")) {
                return true;
            }

            if (this.getPowerOfCard() > comparingCard.getPowerOfCard()) {
                return true;
            } else {
                return false;
            }

        } else if (this.getTypeOFCard() != trumpCard.getTypeOFCard() && comparingCard.getTypeOFCard() == trumpCard.getTypeOFCard()) {
            return false;
        } else {
            return false;
        }


    }
}
