package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.Stack;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;

public class Deck {

    ////////////
    //Properties
    ////////////

    //A single deck object consists of two stacks that hold two different types of cards
    //One stack contains the players character cards, and the other stack contains the players special cards

    public Stack<CardStats> characterCardStatsStack;
    public Stack<CardStats> specialCardStatsStack;

    //TODO: Put in size method for this class to add both sizes of stacks into on integer
    public Deck(){
        this.characterCardStatsStack = new Stack<>();
        this.specialCardStatsStack = new Stack<>();
    }

    /////////////////////
    //Setters and Getters
    /////////////////////


    public Stack<CardStats> getCharacterCardStatsStack() {
        return characterCardStatsStack;
    }

    public void setCharacterCardStatsStack(Stack<CardStats> characterCardStatsStack) {
        this.characterCardStatsStack = characterCardStatsStack;
    }

    public Stack<CardStats> getSpecialCardStatsStack() {
        return specialCardStatsStack;
    }

    public void setSpecialCardStatsStack(Stack<CardStats> specialCardStatsStack) {
        this.specialCardStatsStack = specialCardStatsStack;
    }
}
