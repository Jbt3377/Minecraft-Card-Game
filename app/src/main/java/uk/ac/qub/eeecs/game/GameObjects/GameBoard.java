package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.Button;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

public class GameBoard {

    ////////////
    //Properties
    ////////////

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
    Five containers on the field for Ai cards in play
    Seven containers for players hand, 5 for character cards, 2 for special cards
    Seven containers for Ai hand, 5 for character cards, 2 for special cards
    One container to place the players deck
    One container to place Ai deck
    One container for special cards to be placed
     */

    //////////////
    //Constructor
    /////////////

    public GameBoard(Player human, Player ai, GameScreen gameScreen) {
        this.human = human;
        this.ai = ai;
        this.gameScreen = gameScreen;
        this.fieldContainers = new ArrayList<>();
        this.humanDeck = human.getSelectedDeck();
        this.aiDeck = ai.getSelectedDeck();

        setupContainers();

    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////

    private void setupContainers() {

        int screenWidth = gameScreen.getGame().getScreenWidth();
        int screenHeight = gameScreen.getGame().getScreenHeight();
        int anEighthOfScreenWidth = screenWidth/8;
        int anEighthOfScreenHeight = screenHeight/8;

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));


    }

    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////


    public void update() {

        // ToDo: Update Method

    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        for(MobContainer container: fieldContainers){
            container.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }

    }

    public void update(List<TouchEvent> input, Game mGame){

        for(MobContainer container: fieldContainers){
            container.checkForNewContents(input, mGame);
        }

    }



}