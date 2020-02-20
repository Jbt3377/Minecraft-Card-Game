package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;

public class GameBoard {

    ////////////
    //Properties
    ////////////

    // Two players 'sit down' at the board
    private Human humanPlayer;
    private Ai aiPlayer;

    // Two deck objects, one from each player
    private Deck humanDeck;
    private Deck aiDeck;

    private PlayerHand humanHand;
    private PlayerHand aiHand;


    //Two display boxes to display each players lifePoints
    private GameObject playerLifePointsDisplayBox;
    private GameObject aiLifePointsDisplayBox;

    //Two display boxes to display each players manaPoints7
    private GameObject playerManaPointsDisplayBox;
    private GameObject aiManaPointsDisplayBox;


    // GameScreen to which the board belongs to
    private GameScreen gameScreen;
    private ArrayList<MobContainer> fieldContainers;

    //////////////
    //Constructor
    /////////////

    public GameBoard(Human human, Ai ai, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.fieldContainers = new ArrayList<>();

        this.humanPlayer = human;
        this.aiPlayer = ai;

        // stinky code
        this.humanDeck = gameScreen.getGame().getmDeckManager().constructDeck(humanPlayer.getmSelectedDeckName());
        this.aiDeck = gameScreen.getGame().getmDeckManager().constructDeck(aiPlayer.getmSelectedDeckName());

        setupContainers();

        // Initialise Hands
        humanHand = new PlayerHand(this.humanPlayer, this.humanDeck, gameScreen);
        aiHand = new PlayerHand(this.aiPlayer, this.aiDeck, gameScreen);

    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Method creates & sets positions for containers on game board
     */
    private void setupContainers() {

        int screenWidth = gameScreen.getGame().getScreenWidth();
        int screenHeight = gameScreen.getGame().getScreenHeight();
        int anEighthOfScreenWidth = screenWidth/8;
        int anEighthOfScreenHeight = screenHeight/8;

        // TODO: Adjust positions of containers (raise board up slightly higher)

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


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Draw Containers
        for(MobContainer container: fieldContainers)
            container.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw Hands
        humanHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        aiHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

    }

    public void update(List<TouchEvent> input){


        if(!input.isEmpty()) {

            // Update Containers
            for (MobContainer container : fieldContainers)
                container.checkForNewContents(input, humanPlayer.getSelectedCard());

            // Update Hands
            humanHand.update(input);
            aiHand.update(input);

        }

    }


}