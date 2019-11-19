package uk.ac.qub.eeecs.game.MinecraftCardGame;

import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class MinecraftCardGameScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define a viewport for the board
    private LayerViewport boardLayerViewport;

    private Vector2 touchPosition = new Vector2();

    //Define the background image GameObject
    private GameObject boardBackground;

    //Define pushButtons for the game
    private PushButton endTurnButton;

    // Define a card to be displayed
    private Card card;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public MinecraftCardGameScreen(Game game) {
        super("CardScreen", game);

        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");

        setupViewPorts();

        setupBoardGameObjects();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Setup a full screen viewport for drawing to the entire screen and then
     * setup a board game viewport into the 'world' of the created game objects.
     * Also created a cardLayerViewport for future use
     */

    private void setupViewPorts() {
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());

        //Setup boardLayerViewport - MMC
        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

    }

    private void setupBoardGameObjects() {
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();
        // Create a new, centered card
        card = new Card(450, 600, this);

        //Setup boardBackGround image for board - MMC
        boardBackground =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("BoardBackGround"), this);
        //Setup endTurnButton image for the board - MMC
        endTurnButton = new PushButton(screenWidth * 0.90f, screenHeight/2,screenWidth/10,screenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

    }


    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();
        List<TouchEvent> touchEventList = input.getTouchEvents();
        //Process card touch events
        card.processCardTouchEvents(touchEventList, mGame);


        // Update the card
        card.update(elapsedTime);
        //ViewportHelper.convertScreenPosIntoLayer(mDefaultScreenViewport, card.position, boardLayerViewport, touchPosition);


        //Update the endTurnButton - MMC
        endTurnButton.update(elapsedTime, boardLayerViewport,mDefaultScreenViewport);
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);

        //Draw the background image into boardLayerViewport - MMC
        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

         //Draw the card into cardLayerViewport - MMC
        card.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        //Draw endTurnButton into boardLayerViewport - MMC
        endTurnButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);
    }
}
