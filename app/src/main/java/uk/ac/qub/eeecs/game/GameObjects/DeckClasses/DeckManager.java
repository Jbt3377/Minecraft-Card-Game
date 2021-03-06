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

    // Store of all Card Stats in the Game
    private ArrayList<CardStats> allCardStats;

    // A store of every Deck and the Card Stat id's which make up the associated Deck
    private HashMap<String, int[]> definedDeckStore;
    public HashMap<String, int[]> getDefinedDeckStore() {
        return definedDeckStore;
    }

    //
    private int[] customDeck;
    private boolean customDeckAdded;


    ///////////////
    // Constructor
    //////////////

    public DeckManager(ArrayList<CardStats> allCardStats) {

        this.allCardStats = new ArrayList<>(allCardStats);
        this.customDeckAdded = false;

        definedDeckStore = new HashMap<>();

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
        int[] predefinedDeck0 = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,27,29,32,36};

        // Five predefined decks total
        int[] predefinedDeck1 = new int[] {1,4,6,7,9,11,13,14,15,19,20,22,24,28,29,30,32,34,36};
        int[] predefinedDeck2 = new int[] {3,4,5,6,8,10,14,15,16,17,21,22,23,25,28,29,33,35,36};
        int[] predefinedDeck3 = new int[] {1,2,3,5,6,8,10,11,14,15,18,19,21,24,28,29,30,33,35};
        int[] predefinedDeck4 = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        int[] predefinedDeck5 = new int[] {5,2,7,2,2,3,3,3,7,10,7,9,9,15,15,16,35,35,30,30};

        // Include predefined decks in the Defined Deck store
        definedDeckStore.put("Steve's Arsenal", predefinedDeck0);
        definedDeckStore.put("The Village", predefinedDeck1);
        definedDeckStore.put("Bane of Herobrine", predefinedDeck2);
        definedDeckStore.put("Old McDonald's Farm", predefinedDeck3);
        definedDeckStore.put("Hefty Bois", predefinedDeck4);
        definedDeckStore.put("The End", predefinedDeck5);

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

        // Fetch the Card Id's of a specified deck
        int[] cardIDs = definedDeckStore.get(deckName);

        // Get Associated Card Stats for each Card ID in the deck
        getCardStatsUsingCardIDs(cardIDs, characterCardStatsStack, specialCardStatsStack);

        // Shuffle Deck (Randomise Card Order)
        shuffleDeck(characterCardStatsStack, specialCardStatsStack);

        // Populate deck with the Card Stats Objects
        deck.setCharacterCardStatsStack(characterCardStatsStack);
        deck.setSpecialCardStatsStack(specialCardStatsStack);

        return deck;
    }

    public void addDeck(ArrayList<Integer> customDeckList) {

        ArrayList<Integer> deckList = customDeckList;
        int[] newCustomDeck = new int[deckList.size()];

        int count = 0;
        for (Integer x : deckList){
            newCustomDeck[count++] = x;
        }

        this.customDeck = newCustomDeck;
        this.customDeckAdded = true;
    }

    public void setupCustomDeck() {
        if (customDeckAdded) {
            definedDeckStore.put("Custom Deck", customDeck);
        }
    }

    public boolean isCustomDeckAdded() {
        return customDeckAdded;
    }
}




