package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;


public class DeckManager {

    ////////////
    //Properties
    ////////////

    ArrayList<CardStats> allCardStats;

    //This is the default deck used by both players.
    int[] preDefinedDeck0 = new int[] {0,1,2,3,4,5,6};
    //TODO: Add new pre-defined decks. The numbers match the card id in the AllCardsStats.JSON file. Max size 30?

    Stack<CardStats> characterCardStatsStack = new Stack<>();
    Stack<CardStats> specialCardStatsStack = new Stack<>();


    public DeckManager(ArrayList<CardStats> allCardStats) {
    this.allCardStats = new ArrayList<>(allCardStats);

    }

    public Deck getDefaultDeck(){
        Deck deck = new Deck();
        for (int i: preDefinedDeck0) {
            for (CardStats cs: allCardStats) {
                if(cs.getId() == i){
                    if(cs instanceof CharacterCardStats){
                        characterCardStatsStack.push(cs);
                    }
                    else{
                        specialCardStatsStack.push(cs);
                    }
                }
            }
        }
        Collections.shuffle(characterCardStatsStack);
        Collections.shuffle(specialCardStatsStack);
        deck.setCharacterCardStatsStack(characterCardStatsStack);
        deck.setSpecialCardStatsStack(specialCardStatsStack);
        return deck;
        }


    public Deck retrieveDeck(int deckID){
        Deck deck = new Deck();
        int deckChoice = deckID;

        switch(deckChoice){
            case 0:
                for (int i: preDefinedDeck0) {
                    for (CardStats cs: allCardStats) {
                        if(cs.getId() == i){
                            if(cs instanceof CharacterCardStats){
                                characterCardStatsStack.push(cs);
                            }
                            else{
                                specialCardStatsStack.push(cs);
                            }
                        }
                    }
                }
            default: //Please don't ever reach this line, bugs incoming.....
        }

        Collections.shuffle(characterCardStatsStack);
        Collections.shuffle(specialCardStatsStack);
        deck.setCharacterCardStatsStack(characterCardStatsStack);
        deck.setSpecialCardStatsStack(specialCardStatsStack);
        return deck;
    }

}




