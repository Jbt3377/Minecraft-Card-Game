package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

public abstract class Player {

    ////////////
    //Properties
    ////////////

    //Starting Health and Mana values for all players at object creation.
    private static final int PLAYER_STARTING_HEALTH = 20;
    private static final int PLAYER_STARTING_MANA = 2;


    private int playerHealth, playerMana;
    //DeckID is the current Deck ch0sen by the player.
    private int deckID = 0;

    /////////////
    //Constructor
    /////////////
    public Player() {
        this.playerHealth = PLAYER_STARTING_HEALTH;
        this.playerMana = PLAYER_STARTING_MANA;
    }

    /////////
    //Methods
    /////////

    /////////////////////
    //Getters and Setters
    /////////////////////


    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getPlayerMana() {
        return playerMana;
    }

    public void setPlayerMana(int playerMana) {
        this.playerMana = playerMana;
    }

    public int getDeckID() {
        return deckID;
    }

    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }
}
