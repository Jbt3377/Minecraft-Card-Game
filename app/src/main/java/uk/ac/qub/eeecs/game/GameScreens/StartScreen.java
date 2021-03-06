package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

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
    private CustomBoardScreen CustomBoardScreen;
    private PushButton mDeckEditorButton;
    private DeckEditorScreen DeckEditor;
    private PushButton mAllCards;
    private DisplayCardsScreen DisplayAllCards;
    private ChangeDeckScreen ChangeDeck;
    private PushButton mChangeDeckButton;


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
        assetManager.loadAndAddBitmap("DeckEditorButton", "img/DeckEditorButton.png");
        assetManager.loadAndAddBitmap("CardCollectionButton", "img/CardCollectionButton.png");


        //Setting base volume
        //mGame.getAudioManager().setSfxVolume(0.33f);
        //mGame.getAudioManager().setMusicVolume(0.33f);

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        backgroundLayerViewport = new LayerViewport(screenWidth/2, screenHeight/2,screenWidth/2,screenHeight/2);

        mBackgroundImage = new GameObject(screenWidth/2,screenHeight/2,screenWidth,screenHeight,
                assetManager.getBitmap("StartScreenBackground"), this);

        // Create the trigger buttons

        //Button to start the game
        mCardDemoButton = new PushButton(screenWidth/2,screenHeight/2.75f,screenWidth/6f,screenHeight/10.0f, "StartButton", "StartButton", this);
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

        //Button to view deck editor
        mDeckEditorButton = new PushButton(screenWidth/2.5f,screenHeight/4,screenWidth/5.9f,screenHeight/9.5f, "DeckEditorButton", "DeckEditorButton", this);
        mDeckEditorButton.setPlaySounds(true, true);

        //Button to view rules
        mAllCards= new PushButton(screenWidth/1.7f,screenHeight/4,screenWidth/5.9f,screenHeight/9.5f, "CardCollectionButton", "CardCollectionButton", this);
        mAllCards.setPlaySounds(true, true);

        //Button to view change deck screen
        mChangeDeckButton = new PushButton(screenWidth / 1.10f, screenHeight / 2.7f, screenWidth / 8.2f, screenHeight/ 4.9f, "ChangeDecks",  this);
        mChangeDeckButton.setPlaySounds(true, true);


        //has to be inside the Constructor to create a game screen
        Rules = new RulesScreen(game);
        Options = new OptionsScreen(game);
        DeckEditor = new DeckEditorScreen(game);
        DisplayAllCards = new DisplayCardsScreen(game);
        CustomBoardScreen = new CustomBoardScreen(game);
        ChangeDeck = new ChangeDeckScreen(game);

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

        fps = (int) mGame.getAverageFramesPerSecond();

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
            mAllCards.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);
            mDeckEditorButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);
            mChangeDeckButton.update(elapsedTime, backgroundLayerViewport, mDefaultScreenViewport);


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
                mGame.getScreenManager().addScreen(CustomBoardScreen);
            }
            if (mDeckEditorButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(DeckEditor);
            }
            if (mAllCards.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(DisplayAllCards);
            }
            if (mChangeDeckButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(ChangeDeck);
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
        mAllCards.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mDeckEditorButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mChangeDeckButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);

        if(mGame.isDisplayFps())
            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.9f, mScreenHeight * 0.05f, fpsPaint());

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

    private Paint fpsPaint(){
        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.CENTER);
        fpsPaint.setColor(Color.WHITE);
        return fpsPaint;
    }

}
