package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.ui.Button;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

public class Gameboard {

    ////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////

    //Two players 'sit down' at the board
    private Player human;
    private Player ai;

    //Two deck objects, one from each player
    private Deck humanDeck;
    private Deck aiDeck;

    //Two hands for each player, the cards the currently have in their hand.
    private ArrayList<Card> playerHand;
    private ArrayList<Card> aiHand;

    //Two display boxes to display each players lifePoints
    private GameObject playerLifePointsDisplayBox;
    private GameObject aiLifePointsDisplayBox;

    //Two display boxes to display each players manaPoints
    private GameObject playerManaPointsDisplayBox;
    private GameObject aiManaPointsDisplayBox;

    //Button to end the turn
    private Button endTurnButton;

    // GameScreen to which the board belongs to
    private GameScreen gameScreen;

    private ArrayList<MobContainer> fieldContainers;

    //TODO: Want to add containers to this objects properties. Places where cards are placed etc.
    /*
    Five containers on the field for human cards in play
    Five containers on the field for ai cards in play
    Seven containers for players hand, 5 for character cards, 2 for special cards
    Seven containers for ai hand, 5 for character cards, 2 for special cards
    One container to place the players deck
    One container to place ai deck
     */


    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////

    public Gameboard(Player human, Player ai, GameScreen gameScreen) {
        this.human = human;
        this.ai = ai;
        this.gameScreen = gameScreen;

        this.fieldContainers = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////

    private void setupTestContainer() {

        fieldContainers.add(new MobContainer(300, 300, gameScreen));

    }

    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////


    public void update() {

        // ToDo: Update Method

    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        for(MobContainer container: fieldContainers){
            container.draw(elapsedTime, graphics2D);
        }

    }

}