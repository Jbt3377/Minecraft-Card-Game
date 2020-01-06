package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;
import uk.ac.qub.eeecs.game.platformDemo.PlatformDemoScreen;
import uk.ac.qub.eeecs.game.spaceDemo.SpaceshipDemoScreen;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class StartScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */

    private PushButton mCardDemoButton;
    private PushButton mRulesButton;
    private PushButton mOptionsButton;
    private GameObject mBackgroundImage;
    private LayerViewport backgroundLayerViewport;
    private ScreenViewport backgroundScreenViewport;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public StartScreen(Game game) {
        super("StartScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("StartScreenBackground", "img/StartScreenBackground.png");
        assetManager.loadAndAddBitmap("StartButton", "img/StartButton.png");
        assetManager.loadAndAddBitmap("OptionsButton", "img/OptionsButton.png");
        assetManager.loadAndAddBitmap("RulesButton", "img/RulesButton.png");


        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;


        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        float layerWidth = mDefaultLayerViewport.getWidth();
        float layerHeight = mDefaultLayerViewport.getHeight();


        backgroundLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2.0f,screenWidth/2.0f,screenHeight/2);
        backgroundScreenViewport = new ScreenViewport(0,0,(int)screenWidth,(int)screenHeight);

        // Create the trigger buttons

        //Button to start the game
        mCardDemoButton = new PushButton(layerWidth/2,layerHeight/4,layerWidth/5.0f,layerHeight/10.0f, "StartButton", "StartButton", this);
        mCardDemoButton.setPlaySounds(true, true);

        //Button to view options
        mOptionsButton = new PushButton(layerWidth/2.6f,layerHeight/7.1f,layerWidth/5.0f,layerHeight/10.0f, "OptionsButton", "OptionsButton", this);
        mOptionsButton.setPlaySounds(true, true);

        //Button to view rules
        mRulesButton= new PushButton(layerWidth/1.65f,layerHeight/7.1f,layerWidth/5.0f,layerHeight/10.0f, "RulesButton", "RulesButton", this);
        mRulesButton.setPlaySounds(true, true);

        mBackgroundImage = new GameObject(screenWidth/2,screenHeight/2,screenWidth,screenHeight,
                assetManager.getBitmap("StartScreenBackground"), this);

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            //System.out.println("Touch input detected");
            // Update each button and transition if needed

            mCardDemoButton.update(elapsedTime);

            if (mCardDemoButton.isPushTriggered()){
               mGame.MenuScreentime = elapsedTime.totalTime;
                mGame.getScreenManager().addScreen(new MainGameScreen(mGame));
            }


        }
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        mBackgroundImage.draw(elapsedTime, graphics2D, backgroundLayerViewport, backgroundScreenViewport);
        mCardDemoButton.draw(elapsedTime, graphics2D,mDefaultLayerViewport, mDefaultScreenViewport);
        mOptionsButton.draw(elapsedTime, graphics2D,mDefaultLayerViewport, mDefaultScreenViewport);
        mRulesButton.draw(elapsedTime, graphics2D,mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
