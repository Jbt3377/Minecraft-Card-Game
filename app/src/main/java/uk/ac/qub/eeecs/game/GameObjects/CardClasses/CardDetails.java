package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import android.graphics.Bitmap;


/*
This class is used to store the details for any given card
 */
public class CardDetails {
    int cardID, attackPoints, healthPoints, manaCost;
    String cardName, cardType, cardText, cardPortraitAssetName;

    public CardDetails(int cardID, int attackPoints, int healthPoints, int manaCost, String cardName, String cardType, String cardText, String cardPortraitAssetName) {
        this.cardID = cardID;
        this.attackPoints = attackPoints;
        this.healthPoints = healthPoints;
        this.manaCost = manaCost;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardText = cardText;
        this.cardPortraitAssetName = cardPortraitAssetName;
    }

    public String getCardType() {
        return cardType;
    }
}
