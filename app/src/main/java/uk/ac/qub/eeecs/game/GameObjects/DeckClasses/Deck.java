package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.Stack;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CardDetails;

public class Deck {
    private Stack<CardDetails> characterCardStack;
    private Stack<CardDetails> specialCardStack;


    public Deck(int deckCompositionCode, GameScreen game){
        characterCardStack = game.getGame().getCardLibrary().getCharacterCardDeck(0);
        specialCardStack = game.getGame().getCardLibrary().getSpecialCardDeck(0);
    }

}
