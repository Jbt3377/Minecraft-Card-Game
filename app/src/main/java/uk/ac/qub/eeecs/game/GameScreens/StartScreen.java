package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
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
import uk.ac.qub.eeecs.game.platformDemo.Player;
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
    private RulesScreen Rules;
    private PushButton mOptionsButton;
    private OptionsScreen Options;
    private GameObject mBackgroundImage;
    private LayerViewport backgroundLayerViewport;
    private ScreenViewport backgroundScreenViewport;
    private PushButton mBoardButton;
    private CustomBoardScreen customBoardScreen;


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
        assetManager.loadAndAddBitmap("CustomiseScreenButton", "img/CustomiseScreenButton.png");
        assetManager.loadAndAddMusic("MinecraftMusic","sound/MinecraftMusic.mp3");


        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();
//        float layerWidth = mDefaultLayerViewport.getWidth();
//        float layerHeight = mDefaultLayerViewport.getHeight();

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        backgroundLayerViewport = new LayerViewport(screenWidth/2, screenHeight/2,screenWidth/2,screenHeight/2);
        //backgroundScreenViewport = new ScreenViewport(0,0,(int)screenWidth,(int)screenHeight);

        mBackgroundImage = new GameObject(screenWidth/2,screenHeight/2,screenWidth,screenHeight,
                assetManager.getBitmap("StartScreenBackground"), this);

        // Create the trigger buttons

        //Button to start the game
        mCardDemoButton = new PushButton(screenWidth/2,screenHeight/4,screenWidth/6f,screenHeight/10.0f, "StartButton", "StartButton", this);
        mCardDemoButton.setPlaySounds(true, true);

        //Button to view options
        mOptionsButton = new PushButton(screenWidth/2.5f,screenHeight/7.8f,screenWidth/5.9f,screenHeight/9.5f, "OptionsButton", "OptionsButton", this);
        mOptionsButton.setPlaySounds(true, true);

        //Button to view rules
        mRulesButton= new PushButton(screenWidth/1.7f,screenHeight/7.8f,screenWidth/5.9f,screenHeight/9.5f, "RulesButton", "RulesButton", this);
        mRulesButton.setPlaySounds(true, true);

        //Button to view customise board screen
        mBoardButton = new PushButton(screenWidth / 1.10f, screenHeight / 7.1f, screenWidth / 9.5f, screenHeight/ 5.0f, "CustomiseScreenButton",  this);
        mBoardButton.setPlaySounds(true, true);


        //has to be inside the Constructor to create a game screen
        Rules = new RulesScreen("Rules", game);
        Options = new OptionsScreen("OptionsScreen", game);
        customBoardScreen = new CustomBoardScreen("customBoardScreen", game);





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

        playBackgroundMusic();


        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            //System.out.println("Touch input detected");
            // Update each button and transition if needed

            mCardDemoButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);
            mRulesButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);
            mOptionsButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);
            mBoardButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);


            if (mCardDemoButton.isPushTriggered()){
               mGame.MenuScreentime = elapsedTime.totalTime;
               stopBackGroundMusic();
                mGame.getScreenManager().addScreen(new MainGameScreen(mGame));
            }

            if (mRulesButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(Rules);
            }
            if (mOptionsButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(Options);
            }

            if (mBoardButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(customBoardScreen);
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
        mBackgroundImage.draw(elapsedTime, graphics2D, backgroundLayerViewport, mDefaultScreenViewport);
        mCardDemoButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mOptionsButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mRulesButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mBoardButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);

    }

    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    //Changed string name to new background music
                    getGame().getAssetManager().getMusic("MinecraftMusic"));
    }

    private void stopBackGroundMusic(){
        AudioManager audioManager = getGame().getAudioManager();
        audioManager.stopMusic();
    }
}
