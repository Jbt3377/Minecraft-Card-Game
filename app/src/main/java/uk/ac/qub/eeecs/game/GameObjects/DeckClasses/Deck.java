package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.Stack;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;

public class Deck {

    ////////////
    //Properties
    ////////////

    /** A single deck object consists of two stacks that hold two different types of cards **/

    // One stack contains the Character cards
    private Stack<CardStats> characterCardStatsStack;
    // One stack contains the Special Cards (Equip & Utility)
    private Stack<CardStats> specialCardStatsStack;


    ///////////////
    //Constructors
    //////////////

    /** Constructor **/
    public Deck(){
        this.characterCardStatsStack = new Stack<>();
        this.specialCardStatsStack = new Stack<>();
    }

    /** Copy Constructor **/
    public Deck(Deck deckObjectBeingCloned) {
        this.characterCardStatsStack = deckObjectBeingCloned.characterCardStatsStack;
        this.specialCardStatsStack = deckObjectBeingCloned.specialCardStatsStack;
    }


    ///////////////
    //Methods
    //////////////

    public CardStats popNextCharacterCardStat(){

        if(characterCardStatsStack.peek() != null){
            return characterCardStatsStack.pop();
        }else{
            return null;
        }
    }


    public CardStats popNextSpecialCardStat(){

        if(specialCardStatsStack.peek() != null){
            return specialCardStatsStack.pop();
        }else{
            return null;
        }
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
