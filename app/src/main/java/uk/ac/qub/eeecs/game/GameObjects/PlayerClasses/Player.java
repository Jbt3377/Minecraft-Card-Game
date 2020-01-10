package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;

import android.graphics.Paint;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

public abstract class Player {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final int MAX_EXPERIENCE_POINTS = 20;

    protected int playerNumber;
    protected MainGameScreen gameScreen;
    protected Player opponent;

    protected Deck deck;
    protected int healthPoints, experiencePoints;

    protected ArrayList<String> currentEffects;
    protected Card tempCard;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public Player(int playerNumber, MainGameScreen gameScreen){

        this.playerNumber = playerNumber;
        this.gameScreen = gameScreen;

        this.deck = new Deck(playerNumber, gameScreen);

        this.healthPoints = 20;
        this.experiencePoints = 5;

        currentEffects = new ArrayList<>();


    }

}
