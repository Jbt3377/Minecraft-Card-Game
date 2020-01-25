package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
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
import uk.ac.qub.eeecs.game.GameObjects.Gameboard;
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
    private LayerViewport boardLayerViewport;
    private LayerViewport cardLayerViewport;

    // Background Image
    private GameObject boardBackground;

    // Gameboard associated with Card Game
    private Gameboard gameBoard;

    // Define pushButtons for;

    // MainGameScreen
    private PushButton endTurnButton;
    private PushButton magnificationButton;

    // RulesScreen
    private PushButton RulesScreenButton;
    private RulesScreen Rules;

    // DisplayCardsScreen
    private PushButton displayAllCardsButton;
    private DisplayCardsScreen ViewCards;

    private PushButton pauseButton;

    //Defined an arraylist for the collection of cards
    private ArrayList<Card> cardCollection = new ArrayList<>();
    private int numberOfCards = 4;


    // Temp Variables
    private GameObject pig;
    private int turnNumber = 1;


    //Pause menu
    private PushButton unpauseButton, exitButton;
    private ToggleButton fpsToggle;
    private int fps;
    private Sprite pauseScreen;
    private Paint pausePaint, pausePaint2;
    public boolean gamePaused;
    public boolean displayfps;


    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card Game Screen
     * @param game Game to which this screen belongs
     */
    public MainGameScreen(Game game) {
        super("CardScreen", game);

        // Load Card Image JSONs
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        mGame.getAssetManager().loadCard("txt/assets/MinecraftCardGameScreenCards.JSON");

        //Loading font
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");

        fps = (int) mGame.getAverageFramesPerSecond();
        gamePaused = false;
        displayfps = false;

        setupViewPorts();

        createPauseWindow();

        setupBoardGameObjects();

        //this.gameBoard = new Gameboard(human, ai, this);

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
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        //pig
        pig = new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("PIG"), this);

        // Creates list of card objects
        addCardsToList();

        // Set position of card objects
        setPositionCards();

        // Setup boardBackGround image - MMC
        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("BoardBackGround"), this);

        // Buttons;
        endTurnButton = new PushButton(mScreenWidth * 0.90f, mScreenHeight/2,mScreenWidth/10,mScreenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        pauseButton = new PushButton(mScreenWidth * 0.10f, mScreenHeight/1.2f,mScreenWidth/10,mScreenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        magnificationButton = new PushButton(mScreenWidth * 0.06f, mScreenHeight/10,mScreenWidth/10,mScreenHeight /8,
                "magnifyIcon", this);

        displayAllCardsButton = new PushButton(mScreenWidth * 0.06f, mScreenHeight/3,mScreenWidth/10,mScreenHeight /8,
                "EndTurnDefault", this);

    }


    private void createPauseWindow() {

        // Instantiate pause screen and buttons

        pauseScreen =  new Sprite(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 1.1f,
                mScreenHeight / 1.1f, getGame().getAssetManager().getBitmap("PauseMenu"), this);

        unpauseButton = new PushButton((int) (mScreenWidth / 2.5), (int) (mScreenHeight * 0.2500f), mScreenWidth * 0.208f,
                mScreenHeight * 0.231f, "EndTurnDefault", this);

        exitButton = new PushButton((int) (mScreenWidth / 1.6), (int) (mScreenHeight * 0.2500f), mScreenWidth * 0.208f,
                mScreenHeight * 0.231f, "Redbutton", this);

        fpsToggle = new ToggleButton(mScreenWidth  / 1.3f, mScreenHeight * 0.6700f, mScreenWidth * 0.23f, mScreenHeight * 0.18f,
                "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);


        // Setting up some paints
        pausePaint = new Paint();
        pausePaint.setTextSize(mScreenWidth * 0.0469f);
        pausePaint.setARGB(255, 255, 255, 255);
        pausePaint.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);


        pausePaint2 = new Paint();
        pausePaint2.setTextSize(mScreenWidth * 0.0365f);
        pausePaint2.setARGB(255, 255, 255, 255);
        pausePaint2.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);

    }


    public void drawPauseMenu(ElapsedTime elapsedTime,IGraphics2D graphics2D){

        pauseScreen.draw(elapsedTime, graphics2D);
        graphics2D.drawText("GAME PAUSED", (int) (mScreenWidth / 2.75), mScreenHeight * 0.2037f, pausePaint);
        graphics2D.drawText("FPS Counter:", (int) (mScreenWidth / 3.3), mScreenHeight * 0.35f, pausePaint2);

        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        unpauseButton.draw(elapsedTime, graphics2D,boardLayerViewport,mDefaultScreenViewport);
        exitButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
    }





    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void update(ElapsedTime elapsedTime) {

        if (!gamePaused) {

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

            for (Card c : cardCollection)
                c.processCardTouchEvents(touchEventList, mGame);

            for (int i = 0; i < numberOfCards; i++)
                cardCollection.get(i).update(elapsedTime);

            //checks if the pause button was pressed and if it was changes the control variable


            //Update the endTurnButton - MMC
            endTurnButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

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

        // Draw text that was loaded
        Paint gameTitle = new Paint();
        gameTitle.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        gameTitle.setTextSize(mScreenHeight / 16);
        gameTitle.setTextAlign(Paint.Align.CENTER);

        graphics2D.drawText("Minecraft Card Game", mScreenWidth * 0.5f, mScreenHeight * 0.1f, gameTitle);



        //Load text and font information
        Paint turnText = new Paint();
        turnText.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        turnText.setTextSize(mScreenHeight / 32);
        turnText.setTextAlign(Paint.Align.CENTER);
        graphics2D.drawText("Turn Number: " + turnNumber, mScreenWidth * 0.1f, mScreenHeight * 0.05f, turnText);

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);
        if(displayfps)
            graphics2D.drawText("fps: " + fps, mScreenWidth * 1f, mScreenHeight * 0.05f, fpsPaint);

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
            cardCollection.get(i).setPosition(cardLayerViewport.x-300 + x, cardLayerViewport.y);
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

    public void displayPig(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        if(elapsedTime.totalTime < mGame.MenuScreentime + 5.0) {
            pig.draw(elapsedTime, graphics2D,
                    boardLayerViewport,
                    mDefaultScreenViewport);
        }
    }

    public void RulesButton() {
            if (RulesScreenButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(Rules);
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
        }
    }

    private void displayCardsButton(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        //Draw displayAllCards button
        displayAllCardsButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////

    public PushButton getEndTurnButton() { return endTurnButton; }

    public GameObject getPig() { return pig; }


}












