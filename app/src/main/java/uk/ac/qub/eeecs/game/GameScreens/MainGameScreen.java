package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.TurnManager;


/**
 * MainGameScreen - Class containing the card game and bulk of functionality
 */
public class MainGameScreen extends GameScreen {

    /////////////
    // Properties
    /////////////

    private LayerViewport boardLayerViewport;

    private GameObject boardBackground, player1Heart, player1Mana, player2Heart, player2Mana;
    private GameBoard gameBoard;
    private TurnManager turnManager;

    private PushButton endTurnButton, pauseButton;
    private ToggleButton magnificationButton, fpsToggle;

    private Sound triggerSound = mGame.getAssetManager().getSound("ButtonDefaultPush");
    private int fps;
    private int turnNumber = 1;


    // Pause menu //
    private PushButton unpauseButton, exitButton,volumeButton;
    private int volumecounter = 1;
    private Sprite pauseScreen;
    private Paint pausePaint;
    private boolean gamePaused;


    //////////////
    // Constructor
    //////////////

    /**
     * Public constructor for Main Card Game
     * @param game - Game to which this screen belongs
     */
    public MainGameScreen(Game game) {
        super("CardScreen", game);

        // GameBoard Constructor dependent on type of opponent selected
        if(game.isPlayer2Human())
            gameBoard = new GameBoard(game.getPlayer1(), game.getPlayer2(), this);
        else
            gameBoard = new GameBoard(game.getPlayer1(), game.getAi(), this);

        // Turn Manager Instantiated
        turnManager = new TurnManager(gameBoard,this, game);


        this.gamePaused = false;

        setupViewPorts();

        createPauseWindow();

        setupBoardGameObjects();
    }


    //////////
    // Methods
    //////////

    /**
     * Setup a full screen viewport for drawing to the entire screen and then
     * setup a board game viewport into the 'world' of the created game objects.
     */
    private void setupViewPorts() {
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());

        //Setup boardLayerViewport - MMC
        boardLayerViewport = new LayerViewport(mScreenWidth/2,mScreenHeight/2,mScreenWidth/2,mScreenHeight/2);
    }

    /**
     * Method sets up the bulk of the game objects for the Main Game Screen
     */
    private void setupBoardGameObjects() {

        // Setup boardBackGround image
        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()), this);

        // Buttons
        endTurnButton = new PushButton(mScreenWidth * 0.90f, mScreenHeight/2,mScreenWidth/10, mScreenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        pauseButton = new PushButton(mScreenWidth * 0.044f, mScreenHeight/1.15f,mScreenWidth/14.5f, mScreenHeight/9,
                "PauseButton",  this);

        magnificationButton = new ToggleButton(mScreenWidth * 0.06f, mScreenHeight * 0.08f,mScreenWidth/10,mScreenHeight /8,
                "magnifyIcon", "magnifyIcon","magnifyIcon-active", "magnifyIcon-active" , this);


        magnificationButton.setSounds(triggerSound, triggerSound);

        // Player HP and Mana Icons
        float playerHeartIconXPos = mScreenWidth*0.85f, playerManaIconXPos = mScreenWidth*0.90f,
                player1HeartAndManaYPos = (mScreenHeight*0.60f)+40, player2HeartAndManaYPos = (mScreenHeight*0.40f)-40;

        player1Heart = new GameObject(playerHeartIconXPos, player1HeartAndManaYPos,130, 140, mGame.getAssetManager().getBitmap("Heart"), this);
        player1Mana = new GameObject(playerManaIconXPos, player1HeartAndManaYPos,150, 160, mGame.getAssetManager().getBitmap("BottleOEnchanting"), this);
        player2Heart = new GameObject(playerHeartIconXPos, player2HeartAndManaYPos,130, 140, mGame.getAssetManager().getBitmap("Heart"), this);
        player2Mana = new GameObject(playerManaIconXPos, player2HeartAndManaYPos,150, 160, mGame.getAssetManager().getBitmap("BottleOEnchanting"), this);
    }


    /**
     * Method sets up the Game Objects associated with the Pause Screen
     */
    private void createPauseWindow() {

        pauseScreen =  new Sprite(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 1.1f,
                mScreenHeight / 1.1f, getGame().getAssetManager().getBitmap("PauseMenu"), this);

        unpauseButton = new PushButton((int) (mScreenWidth / 2.9), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ResumeButton", this);

        exitButton = new PushButton((int) (mScreenWidth / 1.5), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ExitButton", this);

        volumeButton = new PushButton(mScreenWidth / 1.35f, mScreenHeight* 0.4700f,mScreenWidth* 0.13f, mScreenHeight* 0.18f,
                "VolumeButton",  this);


        if(mGame.isDisplayFps()){
            fpsToggle = new ToggleButton(mScreenWidth / 1.3f, mScreenHeight * 0.66f, mScreenWidth * 0.20f, mScreenHeight * 0.15f,
                    "ToggleOn", "ToggleOn", "ToggleOff", "ToggleOff", this);
            fpsToggle.setSounds(triggerSound, triggerSound);
        }else {
            fpsToggle = new ToggleButton(mScreenWidth / 1.3f, mScreenHeight * 0.66f, mScreenWidth * 0.20f, mScreenHeight * 0.15f,
                    "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);
            fpsToggle.setSounds(triggerSound, triggerSound);
        }

        if(mGame.isDisplayFps()) fpsToggle.setToggled(true);

        // Setting up some paints
        pausePaint = new Paint();
        pausePaint.setTextSize(mScreenWidth * 0.0469f);
        pausePaint.setARGB(255, 255, 255, 255);
        pausePaint.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);

    }


    /**
     * Method used to draw the pause menu when game paused
     */
    private void drawPauseMenu(ElapsedTime elapsedTime,IGraphics2D graphics2D){

        pauseScreen.draw(elapsedTime, graphics2D);
        graphics2D.drawText("GAME PAUSED", (int) (mScreenWidth / 2.75), mScreenHeight * 0.2037f, pausePaint);
        graphics2D.drawText("FPS Counter:", (int) (mScreenWidth / 4.5), mScreenHeight * 0.37f, pausePaint);
        graphics2D.drawText("Volume:", (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);
        graphics2D.drawText("Volume: " + volumecounter, (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);

        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        unpauseButton.draw(elapsedTime, graphics2D,boardLayerViewport,mDefaultScreenViewport);
        exitButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        volumeButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
    }


    /**
     * Method updates buttons and deals with the result of their altered states
     */
    private void updateGameButtons(ElapsedTime elapsedTime){

        magnificationButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        pauseButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        endTurnButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

        // Check 1 - Magnification Mode enabled
        if (magnificationButton.isToggledOn())
            mGame.setMagnificationToggled(true);
        else
            mGame.setMagnificationToggled(false);

        // Check 2 - Game Paused
        if (pauseButton.isPushTriggered())
         gamePaused = true;
}


    /**
     * Method draws game buttons
     */
    private void drawGameButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        // Draw endTurnButton into boardLayerViewport
        endTurnButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        // Draw magnification button
        magnificationButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);
    }


    /**
     * Method to setup a paint style for turn counter
     * @return - Paint for turn text
     */
    private Paint createTurnTextPaint(){

        Paint turnText = new Paint();
        turnText.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        turnText.setTextSize(mScreenHeight / 32);
        turnText.setTextAlign(Paint.Align.LEFT);
        turnText.setColor(Color.WHITE);
        return turnText;

    }

    /**
     * Method to setup a paint style for player stats
     * @return - Paint for player stats
     */
    private Paint statsTextPaint(){
        Paint statsTextPaint = new Paint();
        statsTextPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        statsTextPaint.setTextSize(mScreenHeight / 30);
        statsTextPaint.setTextAlign(Paint.Align.CENTER);
        statsTextPaint.setColor(Color.WHITE);
        return statsTextPaint;
    }

    /**
     * Method to setup a paint style for fps counter
     * @return - Paint for fps counter
     */
    private Paint fpsPaint(){
        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.CENTER);
        fpsPaint.setColor(Color.WHITE);
        return fpsPaint;
    }


    /**
     * Method checks for end game (Player Health less than 0) and switches
     * to End Game Screen if detected.
     */
    private void checkEndGame(){
        if (gameBoard.getPlayer1().getmPlayerHealth() <= 0) {
            EndGameScreen endGameScreen = new EndGameScreen(getGame(), gameBoard);
            mGame.getScreenManager().addScreen(endGameScreen);
            mGame.getScreenManager().getScreen("endGameScreen");
        }
        else if (gameBoard.getPlayer2().getmPlayerHealth() <= 0) {
            EndGameScreen endGameScreen = new EndGameScreen(getGame(), gameBoard);
            mGame.getScreenManager().addScreen(endGameScreen);
            mGame.getScreenManager().getScreen("endGameScreen");
        }
    }



    ////////////////////////
    // Update & Draw Methods
    ////////////////////////

    @Override
    public void update(ElapsedTime elapsedTime) {

        fps = (int) mGame.getAverageFramesPerSecond();
        if (!gamePaused) {

            updateGameButtons(elapsedTime);

            turnManager.update();

            updatePopUps(elapsedTime);

            checkEndGame();

        } else
            pauseMenuUpdate(elapsedTime);

        gameBoard.update();

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        turnManager.draw(elapsedTime, graphics2D, boardLayerViewport, mDefaultScreenViewport);
        drawGameButtons(elapsedTime, graphics2D);

        drawMagnifiedCard(elapsedTime, graphics2D);

        // Draw player and opponent life points and mana icons
        player1Heart.draw(elapsedTime, graphics2D);
        player1Mana.draw(elapsedTime, graphics2D);
        player2Heart.draw(elapsedTime, graphics2D);
        player2Mana.draw(elapsedTime, graphics2D);

        // Draw player and opponent life points and mana points
        graphics2D.drawText("" + gameBoard.getPlayer1().getmPlayerHealth(), (int) (mScreenWidth * 0.85f), mScreenHeight * 0.645f, statsTextPaint());
        graphics2D.drawText("" + gameBoard.getPlayer1().getmPlayerMana(), (int) (mScreenWidth * 0.902f), mScreenHeight * 0.683f, statsTextPaint());
        graphics2D.drawText("" + gameBoard.getPlayer2().getmPlayerHealth(), (int) (mScreenWidth * 0.85f), mScreenHeight * 0.37f, statsTextPaint());
        graphics2D.drawText("" + gameBoard.getPlayer2().getmPlayerMana(), (int) (mScreenWidth * 0.902f), mScreenHeight * 0.41f, statsTextPaint());


        // Draw Turn Counter
        graphics2D.drawText("Turn Number: " + turnNumber, mScreenWidth * 0.01f, mScreenHeight * 0.05f, createTurnTextPaint());

        // Draw FPS Counter - if enabled
        if(mGame.isDisplayFps())
            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.9f, mScreenHeight * 0.05f, fpsPaint());


        drawPopUps(elapsedTime, graphics2D);

        // Draw Pause Menu / Pause Button - Depending on if game is paused
        if (gamePaused)
            drawPauseMenu(elapsedTime,graphics2D);
        else
            pauseButton.draw(elapsedTime, graphics2D,
                    boardLayerViewport,
                    mDefaultScreenViewport);
    }

    /**
     * Method draws enlarged card if magnification conditions met
     */
    private void drawMagnifiedCard(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        if (mGame.isMagnificationToggled() && mGame.getMagnifiedCard() != null && mGame.drawCard())
            mGame.getMagnifiedCard().draw(elapsedTime, graphics2D);
    }

    /**
     * Method updates objects associated with pause screen
     */
    private void pauseMenuUpdate(ElapsedTime elapsedTime) {
        if (mGame.getInput().getTouchEvents().size() > 0) {

            fpsToggle.update(elapsedTime,boardLayerViewport,mDefaultScreenViewport);
            unpauseButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
            exitButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
            volumeButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);

            // Update displayFps flag
            if(fpsToggle.isToggledOn()){
                mGame.setDisplayFps(true);
            }else{
                mGame.setDisplayFps(false);
            }

            if (unpauseButton.isPushTriggered())
                gamePaused = false;

            if (exitButton.isPushTriggered()) {
                mGame.getAudioManager().stopMusic();
                mGame.getScreenManager().removeScreen(this);
            }

            // Game volume levels updated accordingly
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


    ////////////////////
    // Getters & Setters
    ////////////////////

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public PushButton getEndTurnButton() {
        return endTurnButton;
    }

    public void setEndTurnButton(PushButton endTurnButton) {
        this.endTurnButton = endTurnButton;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public PushButton getUnpauseButton() {
        return unpauseButton;
    }

    public PushButton getpauseButton() {
        return pauseButton;
    }

}