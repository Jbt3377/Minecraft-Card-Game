package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CardStats;

public class CardLibrary {
    //This list stores all possible cards in the library
    ArrayList<CardStats> allCardsInLibrary;

    public CardLibrary(){
        allCardsInLibrary = new ArrayList<>();
        setAllCardsInLibrary();
    }

    private void setAllCardsInLibrary(){
        allCardsInLibrary.add(new CardStats(1,1,1,1,"Cow","CharacterCard","I go boom!"));
        allCardsInLibrary.add(new CardStats(2,2,2,2,"Dragon","CharacterCard","This is a very powerful card"));
        allCardsInLibrary.add(new CardStats(3,3,3,3,"Ender Dragon","CharacterCard","Stronk Card"));
        allCardsInLibrary.add(new CardStats(4,0,0,4,"Sword","SpecialCard","And my sword"));
        allCardsInLibrary.add(new CardStats(5,1,1,1,"Cow","CharacterCard","I go boom!"));
        allCardsInLibrary.add(new CardStats(6,2,2,2,"Dragon","CharacterCard","This is a very powerful card"));
        allCardsInLibrary.add(new CardStats(7,3,3,3,"Ender Dragon","CharacterCard","Stronk Card"));
        allCardsInLibrary.add(new CardStats(8,0,0,4,"Sword","SpecialCard","And my sword"));

    }

    public ArrayList<CardStats> getCardLibrary(){
        return allCardsInLibrary;
    }

    public Stack<CardStats> getDefaultCharacterCardStack(){
        Stack<CardStats> tempStack = new Stack<CardStats>();

        for (CardStats cd: allCardsInLibrary) {
            if(cd.getCardType() == "CharacterCard")
            tempStack.push(cd);
        }

        Collections.shuffle(tempStack);
        return tempStack;
    }

    public Stack<CardStats> getDefaultSpecialCardStack(){
        Stack<CardStats> tempStack = new Stack<CardStats>();

        for(CardStats cd: allCardsInLibrary){
            if(cd.getCardType() == "SpecialCard"){
                tempStack.push(cd);
            }
        }
        Collections.shuffle(tempStack);
        return tempStack;

    }

}
