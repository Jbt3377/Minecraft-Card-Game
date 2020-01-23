package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;


import java.util.HashMap;

public class DeckStore {

    ////////////
    //Properties
    ////////////

    HashMap<Integer, Deck> allDefinedDecks;

    /////////////
    //Constructor
    /////////////

    public DeckStore(){
        this.allDefinedDecks = new HashMap<>();

    }



    /////////
    //Methods
    /////////

    public Deck constructDefaultDeck(){
        Deck d = new Deck();



    return d;
    }

    /////////////////////
    //Getters and Setters
    /////////////////////
 }


