package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

public abstract class Player {

    ////////////
    //Properties
    ////////////

    //Starting Health and Mana values for all players at object creation.
    private static final int PLAYER_STARTING_HEALTH = 20;
    private static final int PLAYER_STARTING_MANA = 2;

    //Health and Mana attributes
    int playerHealth, playerMana;
    //The player's deck
    Deck playerDeck;

    /////////////
    //Constructor
    /////////////
    public Player(Deck standardDeck) {
        this.playerHealth = PLAYER_STARTING_HEALTH;
        this.playerMana = PLAYER_STARTING_MANA;
        this.playerDeck = standardDeck;
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

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
    }
}
