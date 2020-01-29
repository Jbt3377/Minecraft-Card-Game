package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

/*
This class is used to store the details for any given card
 */
public abstract class CardStats {

    ////////////
    //Properties
    ////////////

    private String name;
    private int manaCost;
    private String descText;

    /////////////
    //Constructor
    /////////////

    public CardStats(String name, int manaCost, String descText) {
        this.name = name;
        this.manaCost = manaCost;
        this.descText = descText;
    }


    /////////////////////
    //Getters and Setters
    /////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManacost() {
        return manaCost;
    }

    public void setManacost(int manaCost) {
        this.manaCost = manaCost;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }
}
