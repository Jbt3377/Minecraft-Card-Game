package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;


import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

public abstract class Player {

    //Properties

    //Health and Mana attributes
    int health, mana;

    //The player's deck
    Deck deck;


    //Methods

    public Player(int health, int mana, Deck standardDeck) {
        this.health = health;
        this.mana = mana;
        this.deck = standardDeck;
    }
}
