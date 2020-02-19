package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;

import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

public abstract class Player {

    ////////////
    //Properties
    ////////////

    // Starting Health and Mana values for all players
    private static final int PLAYER_STARTING_HEALTH = 20;
    private static final int PLAYER_STARTING_MANA = 2;


    private int mPlayerHealth, mPlayerMana;

    //DeckID is the current Deck ch0sen by the player.
    private String mSelectedDeckName;

    /////////////
    //Constructor
    /////////////

    public Player(String selectedDeckName) {

        this.mPlayerHealth = PLAYER_STARTING_HEALTH;
        this.mPlayerMana = PLAYER_STARTING_MANA;
        this.mSelectedDeckName = selectedDeckName;
    }

    /////////
    //Methods
    /////////



    /////////////////////
    //Getters and Setters
    /////////////////////


    public int getmPlayerHealth() {
        return mPlayerHealth;
    }

    public void setmPlayerHealth(int mPlayerHealth) {
        this.mPlayerHealth = mPlayerHealth;
    }

    public int getmPlayerMana() {
        return mPlayerMana;
    }

    public void setmPlayerMana(int mPlayerMana) {
        this.mPlayerMana = mPlayerMana;
    }

    public String getmSelectedDeckName() { return mSelectedDeckName; }

    public void setmSelectedDeckName(String mSelectedDeckName) { this.mSelectedDeckName = mSelectedDeckName; }

}
