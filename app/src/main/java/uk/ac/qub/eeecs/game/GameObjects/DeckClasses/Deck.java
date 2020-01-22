package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.Stack;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CardStats;

public class Deck {

    //Properties

    //A single deck object consists of two stacks that hold two different types of cards
    //One stack contains the players character cards, and the other stack contains the players special cards

    private Stack<CardStats> characterCardStatsStack;
    private Stack<CardStats> specialCardStatsStack;


    public Deck(CardLibrary cardLibrary){
        characterCardStatsStack = cardLibrary.getDefaultCharacterCardStack();
        specialCardStatsStack = cardLibrary.getDefaultSpecialCardStack();
    }

}
