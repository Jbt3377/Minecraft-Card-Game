package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.Stack;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CardDetails;

public class Deck {

    //Properties

    //A single deck object consists of two stacks that hold two different types of cards
    //One stack contains the players character cards, and the other stack contains the players special cards

    private Stack<CardDetails> characterCardStack;
    private Stack<CardDetails> specialCardStack;


    public Deck(CardLibrary cardLibrary){
        characterCardStack = cardLibrary.getDefaultCharacterCardStack();
        specialCardStack = cardLibrary.getDefaultSpecialCardStack();
    }

}
