package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.ui.Button;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;

public class Gameboard {

    //Properties

    //Two players 'sit down' at the board
    Player human;
    Player ai;

    //Two deck objects, one from each player
    Deck humanDeck;
    Deck aiDeck;

    //Two hands for each player, the cards the currently have in their hand.
    ArrayList<Card> humanHand;
    ArrayList<Card> aiHand;

    ArrayList<Card> humanCardsOnTheField;
    ArrayList<Card> aiCardsOnTheField;

    //Two display boxes to display each players lifePoints
    GameObject humanLifePointsDisplayBox;
    GameObject aiLifePointsDisplayBox;

    //Two display boxes to display each players manaPoints
    GameObject humanManaPointsDisplayBox;
    GameObject aiManaPointsDisplayBox;

    //Button to end the turn
    Button endTurnButton;

    //TODO: Want to add containers to this objects properties. Places where cards are placed etc.
    /*
    Five containers on the field for human cards in play
    Five containers on the field for Ai cards in play
    Seven containers for players hand, 5 for character cards, 2 for special cards
    Seven containers for Ai hand, 5 for character cards, 2 for special cards
    One container to place the players deck
    One container to place Ai deck
    One container for special cards to be placed
     */


    //Methods

    //Constructor
    public Gameboard(Player human, Player ai) {
        this.human = human;
        this.ai = ai;
    }
}
