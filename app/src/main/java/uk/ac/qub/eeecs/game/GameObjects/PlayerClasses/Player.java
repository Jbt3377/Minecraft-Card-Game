package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;

import java.util.ArrayList;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

public abstract class Player {

    ////////////
    //Properties
    ////////////

    // Starting Health and Mana values for all players
    private static final int PLAYER_STARTING_HEALTH = 100;
    private static final int PLAYER_STARTING_MANA = 10;


    private int mPlayerHealth, mPlayerMana;

    //DeckID is the current Deck ch0sen by the player.
    private String mSelectedDeckName;

    private Card selectedCard;
    private Mob selectedMob;
    private Mob targetedMob;

    int selectedAiContainerIndex;
    int selectedAiCardToMoveIndex;



    private boolean aiFinishedMoves = false;




    ArrayList<Integer> aiCharacterCards = new ArrayList<Integer>();

    /////////////
    //Constructor
    /////////////

    public Player(String selectedDeckName) {

        this.mPlayerHealth = PLAYER_STARTING_HEALTH;
        this.mPlayerMana = PLAYER_STARTING_MANA;
        this.mSelectedDeckName = selectedDeckName;

        this.selectedCard = null;
        this.selectedMob = null;
        this.targetedMob = null;
        this.selectedAiCardToMoveIndex = -1;
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

    public Card getSelectedCard() { return selectedCard;}

    public void setSelectedCard(Card selectedCard) { this.selectedCard = selectedCard; }

    public Mob getSelectedMob() { return selectedMob; }

    public void setSelectedMob(Mob selectedMob) { this.selectedMob = selectedMob; }

    public void setSelectedMobNull(){ this.selectedMob = null;}

    public Mob getTargetedMob() { return targetedMob; }

    public void setTargetedMob(Mob targetedMob) { this.targetedMob = targetedMob; }

    public void setTargetedMobNull(){ this.targetedMob = null;}

    public int getSelectedAiContainerIndex() {
        return selectedAiContainerIndex;
    }

    public void setSelectedAiContainerIndex(int selectedAiContainerIndex) {
        this.selectedAiContainerIndex = selectedAiContainerIndex;
    }

    public int getSelectedAiCardToMoveIndex() {
        return selectedAiCardToMoveIndex;
    }

    public void setSelectedAiCardToMoveIndex(int selectedAiCardToMoveIndex) {
        this.selectedAiCardToMoveIndex = selectedAiCardToMoveIndex;
    }
    public ArrayList<Integer> getAiCharacterCards() {
        return aiCharacterCards;
    }


    public boolean isAiFinishedMoves() {
        return aiFinishedMoves;
    }

    public void setAiFinishedMoves(boolean aiFinishedMoves) {
        this.aiFinishedMoves = aiFinishedMoves;
    }

}
