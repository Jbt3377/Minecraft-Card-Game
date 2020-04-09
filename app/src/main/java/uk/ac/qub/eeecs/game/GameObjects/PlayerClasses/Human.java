package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;


import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;

public class Human extends Player {

    private Card selectedCard;
    private Mob selectedMob;
    private int lifePoints;
    private int manaPoints;


    /////////////
    //Constructor
    /////////////

    public Human(String selectedDeckName) {
        super(selectedDeckName);

        this.selectedCard = null;
        this.selectedMob = null;
        this.lifePoints = 1000;
        this.manaPoints = 100;
    }


    public Card getSelectedCard() { return selectedCard;}

    public void setSelectedCard(Card selectedCard) { this.selectedCard = selectedCard; }

    public Mob getSelectedMob() { return selectedMob; }

    public void setSelectedMob(Mob selectedMob) { this.selectedMob = selectedMob; }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }
}
