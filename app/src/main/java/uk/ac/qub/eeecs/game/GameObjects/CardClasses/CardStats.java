package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

/*
This class is used to store the details for any given card
 */
public class CardStats {
    int cardID, attackPoints, healthPoints, manaCost;
    String cardName, cardType, cardText;

    public CardStats(int cardID, int attackPoints, int healthPoints, int manaCost, String cardName, String cardType, String cardText) {
        this.cardID = cardID;
        this.attackPoints = attackPoints;
        this.healthPoints = healthPoints;
        this.manaCost = manaCost;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardText = cardText;
    }

    public String getCardType() {
        return cardType;
    }
}
