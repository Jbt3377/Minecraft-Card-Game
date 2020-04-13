package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;


import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;

public class Human extends Player {

    private Card selectedCard;
    private Mob selectedMob;
    private Mob targetedMob;


    /////////////
    //Constructor
    /////////////

    public Human(String selectedDeckName) {
        super(selectedDeckName);

        this.selectedCard = null;
        this.selectedMob = null;
        this.targetedMob = null;
    }


    public Card getSelectedCard() { return selectedCard;}

    public void setSelectedCard(Card selectedCard) { this.selectedCard = selectedCard; }

    public Mob getSelectedMob() { return selectedMob; }

    public void setSelectedMob(Mob selectedMob) { this.selectedMob = selectedMob; }

    public void setSelectedMobNull(){ this.selectedMob = null;}

    public Mob getTargetedMob() { return targetedMob; }

    public void setTargetedMob(Mob targetedMob) { this.targetedMob = targetedMob; }

    public void setTargetedMobNull(){ this.targetedMob = null;}

}
