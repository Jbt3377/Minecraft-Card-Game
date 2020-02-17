package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;


import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;

public class Human extends Player {

    private Card selectedCard;
    private Mob selectedMob;

    public Human(Deck selectedDeck) {
        super(selectedDeck);

        this.selectedCard = null;
        this.selectedMob = null;
    }


    public Card getSelectedCard() { return selectedCard;}

    public void setSelectedCard(Card selectedCard) { this.selectedCard = selectedCard; }

    public Mob getSelectedMob() { return selectedMob; }

    public void setSelectedMob(Mob selectedMob) { this.selectedMob = selectedMob; }
}
