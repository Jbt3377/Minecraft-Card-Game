package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
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
    private ToggleButton fpsToggle;

    //Pause menu
    private PushButton unpauseButton, exitButton,volumeButton;
    private int fps;
    private int volumecounter = 1;
    private Sprite pauseScreen;
    private Paint pausePaint;
    public boolean gamePaused, displayfps;


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


        //Setting base volume
        mGame.getAudioManager().setSfxVolume(0.33f);
        mGame.getAudioManager().setMusicVolume(0.33f);

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


        gamePaused = false;
        displayfps = false;

        createPauseWindow();

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    private void createPauseWindow() {

        // Instantiate pause screen and buttons

        pauseScreen =  new Sprite(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 1.1f,
                mScreenHeight / 1.1f, getGame().getAssetManager().getBitmap("PauseMenu"), this);

        unpauseButton = new PushButton((int) (mScreenWidth / 2.9), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ResumeButton", this);

        exitButton = new PushButton((int) (mScreenWidth / 1.5), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ExitButton", this);

        volumeButton = new PushButton(mScreenWidth / 1.35f, mScreenHeight* 0.4700f,mScreenWidth* 0.13f, mScreenHeight* 0.18f,
                "VolumeButton",  this);

        fpsToggle = new ToggleButton(mScreenWidth  / 1.3f, mScreenHeight * 0.66f, mScreenWidth * 0.20f, mScreenHeight * 0.15f,
                "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);


        // Setting up some paints
        pausePaint = new Paint();
        pausePaint.setTextSize(mScreenWidth * 0.0469f);
        pausePaint.setARGB(255, 255, 255, 255);
        pausePaint.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);

    }
    public void drawPauseMenu(ElapsedTime elapsedTime,IGraphics2D graphics2D){

        pauseScreen.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Options", (int) (mScreenWidth / 2.75), mScreenHeight * 0.2037f, pausePaint);
        graphics2D.drawText("FPS Counter:", (int) (mScreenWidth / 4.5), mScreenHeight * 0.37f, pausePaint);
        graphics2D.drawText("Volume:", (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);
        graphics2D.drawText("Volume: " + volumecounter, (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);



        fpsToggle.draw(elapsedTime, graphics2D, backgroundLayerViewport,mDefaultScreenViewport);
        unpauseButton.draw(elapsedTime, graphics2D,backgroundLayerViewport,mDefaultScreenViewport);
        exitButton.draw(elapsedTime, graphics2D, backgroundLayerViewport,mDefaultScreenViewport);
        volumeButton.draw(elapsedTime, graphics2D, backgroundLayerViewport,mDefaultScreenViewport);
    }
    private void updateGameButtons(ElapsedTime elapsedTime){


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

            if (mBoardButton.isPushTriggered()) {
                mGame.MenuScreentime = elapsedTime.totalTime;
                stopBackGroundMusic();
                mGame.getScreenManager().addScreen(customBoardScreen);
            }
            if (mOptionsButton.isPushTriggered())
                gamePaused = true;

        }
    }
    private Paint createFPSTextPaint(){

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);
        fpsPaint.setColor(Color.WHITE);
        return fpsPaint;

    }
    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        playBackgroundMusic();

        fps = (int) mGame.getAverageFramesPerSecond();
        if (!gamePaused) {

            updateGameButtons(elapsedTime);



            updatePopUps(elapsedTime);
        } else
            pauseMenuUpdate(elapsedTime);

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
        mRulesButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);
        mBoardButton.draw(elapsedTime, graphics2D,backgroundLayerViewport, mDefaultScreenViewport);

        if(displayfps)
            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.99f, mScreenHeight * 0.05f, createFPSTextPaint());
        // Draw Pause Menu / Pause Button - Depending on if game is paused
        if (gamePaused)
            drawPauseMenu(elapsedTime,graphics2D);
        else
            mOptionsButton.draw(elapsedTime, graphics2D,
                    backgroundLayerViewport,
                    mDefaultScreenViewport);

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

    private void pauseMenuUpdate(ElapsedTime elapsedTime) {
        if (mGame.getInput().getTouchEvents().size() > 0) {

            fpsToggle.update(elapsedTime,backgroundLayerViewport,mDefaultScreenViewport);
            unpauseButton.update(elapsedTime ,backgroundLayerViewport,mDefaultScreenViewport);
            exitButton.update(elapsedTime ,backgroundLayerViewport,mDefaultScreenViewport);
            volumeButton.update(elapsedTime ,backgroundLayerViewport,mDefaultScreenViewport);

            if (unpauseButton.isPushTriggered())
                gamePaused = false;

            if (exitButton.isPushTriggered()) {
                mGame.getAudioManager().stopMusic();
                mGame.getScreenManager().removeScreen(this);
            }

            if (fpsToggle.isToggledOn()) {
                displayfps = true;
            }else
                displayfps =false;

            if(volumeButton.isPushTriggered()){

                if(volumecounter == 0){
                    mGame.getAudioManager().setSfxVolume(0.33f);
                    mGame.getAudioManager().setMusicVolume(0.33f);
                    volumecounter++;
                }else if(volumecounter == 1){

                    mGame.getAudioManager().setSfxVolume(0.67f);
                    mGame.getAudioManager().setMusicVolume(0.67f);
                    volumecounter++;
                }else if(volumecounter == 2) {

                    mGame.getAudioManager().setSfxVolume(1);
                    mGame.getAudioManager().setMusicVolume(1);
                    volumecounter++;
                } else if (volumecounter == 3) {
                    mGame.getAudioManager().setSfxVolume(0);
                    mGame.getAudioManager().setMusicVolume(0);
                    volumecounter = 0;
                }
            }

        }
    }
}
