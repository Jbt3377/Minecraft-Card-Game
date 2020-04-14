package uk.ac.qub.eeecs.game.GameObjects.DeckClasses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Stack;

import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;


public class DeckManager {

    ////////////
    //Properties
    ////////////

    // List containing all card stat objects
    private ArrayList<CardStats> allCardStats;

    // A store of every Deck and the Cards which make up that associated Deck
    private HashMap<String, int[]> definedDeckStore;


    ///////////////
    // Constructor
    //////////////

    public DeckManager(ArrayList<CardStats> allCardStats) {

        this.allCardStats = new ArrayList<>(allCardStats);

        definedDeckStore = new HashMap<String, int[]>();

        setupPredefinedDecks();

    }

    //////////
    // Methods
    //////////

    /**
     * Method creates a set of predefined decks. Adds them to Defined Deck store
     */
    private void setupPredefinedDecks(){

        // First predefined deck acts as default assigned deck
        int[] predefinedDeck0 = new int[] {0,1,2,3,4,5,6};

        // Five predefined decks total
        int[] predefinedDeck1 = new int[] {1,2,3,4,5,6,0};
        int[] predefinedDeck2 = new int[] {2,3,4,5,6,0,1};
        int[] predefinedDeck3 = new int[] {3,4,5,6,0,1,2};
        int[] predefinedDeck4 = new int[] {4,5,6,0,1,2,3};

        // Include predefined decks in the Defined Deck store
        definedDeckStore.put("Steve's Arsenal", predefinedDeck0);
        definedDeckStore.put("The Village", predefinedDeck1);
        definedDeckStore.put("Bane of Herobrine", predefinedDeck2);
        definedDeckStore.put("Old McDonald's Farm", predefinedDeck3);
        definedDeckStore.put("Hefty Bois", predefinedDeck4);

    }



    /**
     * Method will search for the associated Card Stats object for every Card ID
     * @param cardIDs - Array of Card IDs representing the Cards making up a specified Deck
     */
    private void getCardStatsUsingCardIDs(int[] cardIDs, Stack<CardStats> characterCardStatsStack,
                                          Stack<CardStats> specialCardStatsStack){

        // For each Card ID, search for associated Card Stats
        for (int i : cardIDs) {

            for (CardStats cs : allCardStats) {
                if (cs.getId() == i) {

                    // When stats found, push to appropriate stack
                    if (cs instanceof CharacterCardStats)
                        characterCardStatsStack.push(cs);
                    else
                        specialCardStatsStack.push(cs);
                }
            }
        }
    }



    /**
     * Method Shuffle Deck - Randomising Card order in each Stack
     */
    private void shuffleDeck(Stack<CardStats> characterCardStatsStack,
                        Stack<CardStats> specialCardStatsStack){

        // Shuffle Stacks - Randomising Card order in Deck
        Collections.shuffle(characterCardStatsStack);
        Collections.shuffle(specialCardStatsStack);

    }



    /**
     * Method constructs and returns a requested deck
     * @param deckName - Deck Requested
     * @return deck - Constructed Deck
     */
    public Deck constructDeck(String deckName) {

        Deck deck = new Deck();
        Stack<CardStats> characterCardStatsStack = new Stack<>();
        Stack<CardStats> specialCardStatsStack = new Stack<>();

        // Array of cardIDs identify the cards of a deck
        int[] cardIDs = definedDeckStore.get(deckName);

        // Get Card Stats for each Card ID in the deck
        getCardStatsUsingCardIDs(cardIDs, characterCardStatsStack, specialCardStatsStack);

        // Shuffle Deck (Randomise Card Order)
        shuffleDeck(characterCardStatsStack, specialCardStatsStack);

        // Assign constructed Stacks to the Deck's Stacks
        deck.setCharacterCardStatsStack(characterCardStatsStack);
        deck.setSpecialCardStatsStack(specialCardStatsStack);

        return deck;
    }

}




