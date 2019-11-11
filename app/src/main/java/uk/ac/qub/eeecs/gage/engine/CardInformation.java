package uk.ac.qub.eeecs.gage.engine;

public class CardInformation {

    //variables
    private String assetName;

    private String cardName;

    private String description;

    private int attack;

    private int defence;


    public CardInformation(String assetName, String cardName, String description, int attack, int defence ){
        this.assetName = assetName;
        this.cardName = cardName;
        this.description = description;
        this.attack = attack;
        this.defence = defence;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getCardName() {
        return cardName;
    }

    public String getDescription() {
        return description;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }
}
