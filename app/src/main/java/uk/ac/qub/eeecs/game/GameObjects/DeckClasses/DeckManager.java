package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;

import java.util.HashMap;
import java.util.Stack;

import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;


public class DeckManager {

    ////////////
    //Properties
    ////////////

    private HashMap<Integer, Deck> deckStore;
    Deck playerDeck;
    Deck aiDeck;

    CharacterCardStats c0 = new CharacterCardStats("Cow",3, "Moo", 10, 2);
    CharacterCardStats c1 = new CharacterCardStats("Dragon",6, "Flame", 20, 6);
    CharacterCardStats c2 = new CharacterCardStats("Creeper",3, "Boom", 12, 3);
    CharacterCardStats c3 = new CharacterCardStats("Zombie",1, "Dead", 5, 1);
    CharacterCardStats c4 = new CharacterCardStats("Pig",1, "Oink", 50, 30);

    Stack<CardStats> characterCardStatsStack = new Stack<>();
    Stack<CardStats> specialCardStatsStack = new Stack<>();


    public DeckManager() {
    characterCardStatsStack.push(c0);
    characterCardStatsStack.push(c1);
    characterCardStatsStack.push(c2);
    characterCardStatsStack.push(c3);
    characterCardStatsStack.push(c4);

    this.playerDeck = new Deck(characterCardStatsStack, specialCardStatsStack);
    this.aiDeck = new Deck(characterCardStatsStack, specialCardStatsStack);
    }


    public Deck getPlayerDeck(){
        return playerDeck;
    }

    public Deck getAiDeck() {
        return aiDeck;

    }
}




