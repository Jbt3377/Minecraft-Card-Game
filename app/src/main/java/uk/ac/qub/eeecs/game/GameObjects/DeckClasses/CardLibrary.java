package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CardDetails;

public class CardLibrary {
    //This list stores all possible cards in the library
    ArrayList<CardDetails> allCardsInLibrary;

    public CardLibrary(){
        allCardsInLibrary = new ArrayList<>();
        setAllCardsInLibrary();
    }

    private void setAllCardsInLibrary(){
        allCardsInLibrary.add(new CardDetails(1,1,1,1,"Cow","CharacterCard","I go boom!","cow"));
        allCardsInLibrary.add(new CardDetails(2,2,2,2,"Dragon","CharacterCard","This is a very powerful card","dragon"));
        allCardsInLibrary.add(new CardDetails(3,3,3,3,"Ender Dragon","CharacterCard","Stronk Card","dragon"));
        allCardsInLibrary.add(new CardDetails(4,0,0,4,"Sword","SpecialCard","And my sword","sword"));
        allCardsInLibrary.add(new CardDetails(5,1,1,1,"Cow","CharacterCard","I go boom!","cow"));
        allCardsInLibrary.add(new CardDetails(6,2,2,2,"Dragon","CharacterCard","This is a very powerful card","dragon"));
        allCardsInLibrary.add(new CardDetails(7,3,3,3,"Ender Dragon","CharacterCard","Stronk Card","dragon"));
        allCardsInLibrary.add(new CardDetails(8,0,0,4,"Sword","SpecialCard","And my sword","sword"));

    }

    public ArrayList<CardDetails> getCardLibrary(){
        return allCardsInLibrary;
    }

    public Stack<CardDetails> getDefaultCharacterCardStack(){
        Stack<CardDetails> tempStack = new Stack<CardDetails>();

        for (CardDetails cd: allCardsInLibrary) {
            if(cd.getCardType() == "CharacterCard")
            tempStack.push(cd);
        }

        Collections.shuffle(tempStack);
        return tempStack;
    }

    public Stack<CardDetails> getDefaultSpecialCardStack(){
        Stack<CardDetails> tempStack = new Stack<CardDetails>();

        for(CardDetails cd: allCardsInLibrary){
            if(cd.getCardType() == "SpecialCard"){
                tempStack.push(cd);
            }
        }
        Collections.shuffle(tempStack);
        return tempStack;

    }

}
