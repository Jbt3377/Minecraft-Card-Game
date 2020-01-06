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

    }

    public ArrayList<CardDetails> getCardLibrary(){
        return allCardsInLibrary;
    }

    public Stack<CardDetails> getCharcterCardDeck(int deckCompositionCode) {
        if (deckCompositionCode == 1) {
            //TODO: Implement functionality for different deck types
        }
        if (deckCompositionCode == 2) {
            //TODO: Implement functionality for different deck types
        }
        if (deckCompositionCode == 3) {
            //TODO: Implement functionality for different deck types
        }
        return getDefaultCharacterCardDeck();
    }

    public Stack<CardDetails> getSpecialCardDeck(int deckCompositionCode){
        if (deckCompositionCode == 1) {
            //TODO: Implement functionality for different deck types
        }
        if (deckCompositionCode == 2) {
            //TODO: Implement functionality for different deck types
        }
        if (deckCompositionCode == 3) {
            //TODO: Implement functionality for different deck types
        }
        return getDefaultSpecialCardDeck();
    }



    private Stack<CardDetails> getDefaultCharacterCardDeck(){
        Stack<CardDetails> deck = new Stack<CardDetails>();

        for (CardDetails cd: allCardsInLibrary) {
            if(cd.getCardType() == "CharacterCard")
            deck.push(cd);
        }

        Collections.shuffle(deck);
        return deck;
    }

    private Stack<CardDetails> getDefaultSpecialCardDeck(){
        Stack<CardDetails> deck = new Stack<CardDetails>();

        for(CardDetails cd: allCardsInLibrary){
            if(cd.getCardType() == "SpecialCard"){
                deck.push(cd);
            }
        }
        Collections.shuffle(deck);
        return deck;

    }

}
