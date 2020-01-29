package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Interaction;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;


/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class MainGameScreen extends GameScreen {

    ////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////

    // Viewports
    private LayerViewport boardLayerViewport, cardLayerViewport;

    // Background Image
    private GameObject boardBackground;

    // Game Board associated with Card Game
    private GameBoard gameBoard;

    // MainGameScreen
    private PushButton endTurnButton, displayAllCardsButton, pauseButton;
    private ToggleButton magnificationButton, fpsToggle;

    // DisplayCardsScreen
    private DisplayCardsScreen ViewCards;

    //Defined an arraylist for the collection of cards
    private ArrayList<Card> cardCollection = new ArrayList<>();
    private int numberOfCards = 4;

    //Turn Number Counter
    private Paint dialogueTextPaint;
    private Vector2 textPosition;

    //Turn Number Counter
    private int turnNumber = 1;


    //Pause menu
    private PushButton unpauseButton, exitButton,volumeButton;
    private int fps;
    private int volumecounter = 1;
    private Sprite pauseScreen;
    private Paint pausePaint;
    public boolean gamePaused, displayfps;


    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card Game Screen
     * @param game Game to which this screen belongs
     */
    public MainGameScreen(Game game) {
        super("CardScreen", game);

        gameBoard = new GameBoard(game.getHuman(), game.getAi(), this);


        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");

        mGame.getAssetManager().loadCard("txt/assets/MinecraftCardGameScreenCards.JSON");
        mGame.getAssetManager().loadAndAddMusic("MinecraftMusic","sound/MinecraftMusic.mp3");



        gamePaused = false;
        displayfps = false;

        setupViewPorts();

        createPauseWindow();

        setupBoardGameObjects();

        //Loading font
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");

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
        boardLayerViewport = new LayerViewport(mScreenWidth/2,mScreenHeight/2,mScreenWidth/2,mScreenHeight/2);
        cardLayerViewport = new LayerViewport(mScreenWidth/2,mScreenHeight/2,mScreenWidth/2,mScreenHeight/2);
    }


    private void setupBoardGameObjects() {

        // Creates list of card objects
        addCardsToList();

        // Set position of card objects
        setPositionCards();

        // Setup boardBackGround image - MMC
        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()), this);

        // Buttons;
        endTurnButton = new PushButton(mScreenWidth * 0.90f, mScreenHeight/2,mScreenWidth/10, mScreenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        pauseButton = new PushButton(mScreenWidth * 0.044f, mScreenHeight/1.15f,mScreenWidth/14.5f, mScreenHeight/9,
                "PauseButton",  this);


        magnificationButton = new ToggleButton(mScreenWidth * 0.06f, mScreenHeight * 0.08f,mScreenWidth/10,mScreenHeight /8,
                "magnifyIcon", "magnifyIcon","magnifyIcon-active", "magnifyIcon-active" , this);



        displayAllCardsButton = new PushButton(mScreenWidth * 0.06f, mScreenHeight/3,mScreenWidth/10, mScreenHeight /8,
                "EndTurnDefault", this);

    }


    private void createPauseWindow() {

        // Instantiate pause screen and buttons

        pauseScreen =  new Sprite(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 1.1f,
                mScreenHeight / 1.1f, getGame().getAssetManager().getBitmap("PauseMenu"), this);

        unpauseButton = new PushButton((int) (mScreenWidth / 2.9), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ResumeButton", this);

        exitButton = new PushButton((int) (mScreenWidth / 1.5), (int) (mScreenHeight * 0.15f), mScreenWidth * 0.208f,
                mScreenHeight * 0.15f, "ExitButton", this);

        volumeButton = new PushButton(mScreenWidth / 1.3f, mScreenHeight* 0.4700f,mScreenWidth* 0.23f, mScreenHeight* 0.18f,
                "PauseButton",  this);

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
        graphics2D.drawText("GAME PAUSED", (int) (mScreenWidth / 2.75), mScreenHeight * 0.2037f, pausePaint);
        graphics2D.drawText("FPS Counter:", (int) (mScreenWidth / 4.5), mScreenHeight * 0.37f, pausePaint);
        graphics2D.drawText("Voulume:", (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);
        graphics2D.drawText("Voulume: " + volumecounter, (int) (mScreenWidth / 3.3), mScreenHeight * 0.55f, pausePaint);
        graphics2D.drawText("Voulume: " + mGame.getAudioManager().getMusicVolume() , (int) (mScreenWidth / 3.3), mScreenHeight * 0.65f, pausePaint);


        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        unpauseButton.draw(elapsedTime, graphics2D,boardLayerViewport,mDefaultScreenViewport);
        exitButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        volumeButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
    }





    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void update(ElapsedTime elapsedTime) {

        fps = (int) mGame.getAverageFramesPerSecond();
        if (!gamePaused) {


            //Toggle Button Update
            magnificationButton(elapsedTime);

            // Process any touch events occurring since the last update
            Input input = mGame.getInput();
            List<TouchEvent> touchEventList = input.getTouchEvents();


            pauseButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
            if (pauseButton.isPushTriggered())
                gamePaused = true;


            displayAllCardsButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
            if (displayAllCardsButton.isPushTriggered()){
                //Game Screen Display
                mGame.MenuScreentime = elapsedTime.totalTime;
                mGame.getScreenManager().addScreen(new DisplayCardsScreen(mGame));

            }

            for (Card c : cardCollection) {
                //c.processCardTouchEvents(touchEventList, mGame);
                Interaction.processDragEvents(touchEventList, c ,mGame);
            }


            for (int i = 0; i < numberOfCards; i++) {
                cardCollection.get(i).update(elapsedTime);
            }
            //checks if the pause button was pressed and if it was changes the control variable


            //Update the endTurnButton - MMC
            endTurnButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

            gameBoard.update(touchEventList, mGame);

            EndTurn();

            updatePopUps(elapsedTime);
        } else
            pauseMenuUpdate(elapsedTime);


    }



    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        graphics2D.clear(Color.WHITE);

        // Draw the background image into boardLayerViewport - MMC
        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        gameBoard.draw(elapsedTime, graphics2D, boardLayerViewport, mDefaultScreenViewport);

        // Displays Cards
        displayCards(elapsedTime, graphics2D);


        // Draw endTurnButton into boardLayerViewport - MMC
        endTurnButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);


        // Draw magnification button
        magnificationButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);




        displayCardsButton(elapsedTime, graphics2D);

        //Old title on game screen (Not Required anymore)
        // Draw text that was loaded
        /*
        Paint gameTitle = new Paint();
        gameTitle.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        gameTitle.setTextSize(mScreenHeight / 16);
        gameTitle.setTextAlign(Paint.Align.CENTER);

        graphics2D.drawText("Minecraft Card Game", mScreenWidth * 0.5f, mScreenHeight * 0.1f, gameTitle);
        */


        //Load text and font information
        Paint turnText = new Paint();
        turnText.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        turnText.setTextSize(mScreenHeight / 32);
        turnText.setTextAlign(Paint.Align.LEFT);
        turnText.setColor(Color.WHITE);
        graphics2D.drawText("Turn Number: " + turnNumber, mScreenWidth * 0.01f, mScreenHeight * 0.05f, turnText);

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);
        fpsPaint.setColor(Color.WHITE);
        if(displayfps)
            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.99f, mScreenHeight * 0.05f, fpsPaint);

        drawPopUps(elapsedTime, graphics2D);


        //making use of the control variable defined
        if (gamePaused)
            drawPauseMenu(elapsedTime,graphics2D);
        else
            pauseButton.draw(elapsedTime, graphics2D,
                    boardLayerViewport,
                    mDefaultScreenViewport);


    }


    /**
     * Method to add card objects to card collection array list - AB
     */
    private void addCardsToList(){
        ArrayList<CardInformation> cards = mGame.getAssetManager().getCards();

        for(int i = 0; i < cards.size(); i++){
            Card card = new Card(cardLayerViewport.x, cardLayerViewport.y, this, i);
            cardCollection.add(card);
        }
    }


    /**
     *  Method to set position of cards in card collection array list - AB
     */
    private void setPositionCards(){
        //View Port is set to centre of screen - Note view port is not screen size calibrated
        for(int i = 0; i < numberOfCards; i++){
            int x = i * 200;  //Variable for distance between cards
            cardCollection.get(i).setNewPosition(cardLayerViewport.x-300 + x, cardLayerViewport.y);
        }
    }


    /**
     * Method used to draw cards from card collection - AB
     * @param elapsedTime
     * @param graphics2D
     */
    private void displayCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < numberOfCards; i++){
            cardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardLayerViewport,
                    mDefaultScreenViewport);
        }
    }



    public void magnificationButton(ElapsedTime elapsedTime) {
        magnificationButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

        if (magnificationButton.isToggledOn()) {
                mGame.setMagnificationToggled(true);

            }
            else {
                mGame.setMagnificationToggled(false);

            }
    }




    public void EndTurn() {
        if (endTurnButton.isPushTriggered()) {
            turnNumber++;
            new PopUpObject(mGame.getScreenWidth() / 2, mGame.getScreenHeight() / 2, getGame().getAssetManager().getBitmap("PopupSign"), this, 50, "Turn Ended");
        }
    }
    private void pauseMenuUpdate(ElapsedTime elapsedTime) {
        if (mGame.getInput().getTouchEvents().size() > 0) {

            fpsToggle.update(elapsedTime,boardLayerViewport,mDefaultScreenViewport);
            unpauseButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
            exitButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
            volumeButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);

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

    private void displayCardsButton(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        //Draw displayAllCards button
        displayAllCardsButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
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

    ////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////


    }








