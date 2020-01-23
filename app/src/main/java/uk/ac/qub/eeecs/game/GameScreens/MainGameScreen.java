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
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;


/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class MainGameScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define a viewport for the board
    private LayerViewport boardLayerViewport;

    //Define the background image GameObject
    private GameObject boardBackground;

    //Define pushButtons for the game
    private PushButton endTurnButton;
    private ToggleButton magnificationButton;

    //RulesScreen Buttons
    private PushButton RulesScreenButton;
    private RulesScreen Rules;

    //displayAllCards Buttons
    private PushButton displayAllCardsButton;
    private DisplayCardsScreen ViewCards;

    //private pig :)
    private GameObject pig;
    private PushButton pauseButton;
    //Defined a number for the number of cards
    private int numberOfCards = 4;

    //Defined an arraylist for the collection of cards
    private ArrayList<Card> cardCollection = new ArrayList<>();

    private LayerViewport cardLayerViewport;

    private Paint dialogueTextPaint;
    private Vector2 textPosition;

    //Turn Number Counter
    private int turnNumber = 1;


    //Pause menu
    private PushButton unpauseButton, exitButton;
    private ToggleButton fpsToggle;
    private int fps;
    public boolean displayfps;
    private Sprite pauseScreen;
    private Paint pausePaint, pausePaint2;
    public boolean gamePaused;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public MainGameScreen(Game game) {
        super("CardScreen", game);
        GameBoard gameboard = new GameBoard(game.getHuman(), game.getAi());


        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");

        mGame.getAssetManager().loadCard("txt/assets/MinecraftCardGameScreenCards.JSON");


        fps = (int) mGame.getAverageFramesPerSecond();
         gamePaused = false;
         displayfps = false;

        setupViewPorts();
        //creating the actual pause menu
        createPauseWindow();

        setupBoardGameObjects();

        //Loading font
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");

    }


    //getters for the buttons (using in unit test)
    public PushButton getEndTurnButton() { return endTurnButton; }

    public GameObject getPig() {
        return pig;
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
        cardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
    }

    private void setupBoardGameObjects() {
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        //pig
        pig = new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("PIG"), this);

        //Creates a list of card objects
        addCardsToList();

        //Sets the position of cards
        setPositionCards();

        //Setup boardBackGround image for board - MMC
        boardBackground =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("BoardBackGround"), this);
        //Setup endTurnButton image for the board - MMC
        endTurnButton = new PushButton(screenWidth * 0.90f, screenHeight/2,screenWidth/10,screenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        pauseButton = new PushButton(screenWidth * 0.10f, screenHeight/1.2f,screenWidth/10,screenHeight/10,
                "EndTurnDefault", "EndTurnActive", this);

        //Setup magnification button for the board
        magnificationButton = new ToggleButton(screenWidth * 0.06f, screenHeight * 0.08f,screenWidth/10,screenHeight /8,
                "magnifyIcon", "magnifyIcon","magnifyIcon-active", "magnifyIcon-active" , this);



        //Setup displayCards  button for the board
        displayAllCardsButton = new PushButton(screenWidth * 0.06f, screenHeight/3,screenWidth/10,screenHeight /8,
                "EndTurnDefault", this);



    }


    private void createPauseWindow() {
//set up fresh variables to use within this method of screen sizes
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

//loading in pause screen and buttons

        pauseScreen =  new Sprite(screenWidth / 2, screenHeight / 2, screenWidth / 1.1f,
                screenHeight / 1.1f, getGame().getAssetManager().getBitmap("PauseMenu"), this);

        unpauseButton = new PushButton((int) (screenWidth / 2.5), (int) (screenHeight * 0.2500f), screenWidth * 0.208f,
                screenHeight * 0.231f, "EndTurnDefault", this);

        exitButton = new PushButton((int) (screenWidth / 1.6), (int) (screenHeight * 0.2500f), screenWidth * 0.208f,
                screenHeight * 0.231f, "Redbutton", this);

        fpsToggle = new ToggleButton(screenWidth  / 1.3f, screenHeight * 0.6700f, screenWidth * 0.23f, screenHeight * 0.18f,
                "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);






        //Setting up some paints
        pausePaint = new Paint();
        pausePaint.setTextSize(screenWidth * 0.0469f);
        pausePaint.setARGB(255, 255, 255, 255);
        pausePaint.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);


        pausePaint2 = new Paint();
        pausePaint2.setTextSize(screenWidth * 0.0365f);
        pausePaint2.setARGB(255, 255, 255, 255);
        pausePaint2.setTypeface(MainActivity.minecraftRegFont);
        pausePaint.setColor(Color.BLACK);

    }

//Method to draw the pause menu created to keep code simpla and not as messy
    public void drawPauseMenu(ElapsedTime elapsedTime,IGraphics2D graphics2D){
    //variables of screen size
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();


        pauseScreen.draw(elapsedTime, graphics2D);
        graphics2D.drawText("GAME PAUSED", (int) (screenWidth / 2.75), screenHeight * 0.2037f, pausePaint);
        graphics2D.drawText("FPS Counter:", (int) (screenWidth / 3.3), screenHeight * 0.35f, pausePaint2);

        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        unpauseButton.draw(elapsedTime, graphics2D,boardLayerViewport,mDefaultScreenViewport);
        exitButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */


    @Override
    public void update(ElapsedTime elapsedTime) {

        //Toggle Button Update
        magnificationButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);


        if (!gamePaused) {

            // Process any touch events occurring since the last update
            Input input = mGame.getInput();
            List<TouchEvent> touchEventList = input.getTouchEvents();

            pauseButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
            if (pauseButton.isPushTriggered()){
                gamePaused = true;
            }

            displayAllCardsButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
            if (displayAllCardsButton.isPushTriggered()){
                //Game Screen Display
                mGame.MenuScreentime = elapsedTime.totalTime;
                mGame.getScreenManager().addScreen(new DisplayCardsScreen(mGame));

            }

            for (Card c : cardCollection) {
                c.processCardTouchEvents(touchEventList, mGame);
            }

            for (int i = 0; i < numberOfCards; i++) {
                cardCollection.get(i).update(elapsedTime);
            }
            //checks if the pause button was pressed and if it was changes the control variable


            //Update the endTurnButton - MMC
            endTurnButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

            EndTurn();

            updatePopUps(elapsedTime);
        } else
            pauseMenuUpdate(elapsedTime);


    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override



    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        graphics2D.clear(Color.WHITE);

        //Draw the background image into boardLayerViewport - MMC
        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);


        //Displays Cards
        displayCards(elapsedTime, graphics2D);


        //Draw endTurnButton into boardLayerViewport - MMC
        endTurnButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);


        //Draw magnification button
        magnificationButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);


        displayCardsButton(elapsedTime, graphics2D);

        //Draw text that was loaded
        Paint gameTitle = new Paint();
        gameTitle.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        gameTitle.setTextSize(height / 16);
        gameTitle.setTextAlign(Paint.Align.CENTER);

        graphics2D.drawText("Minecraft Card Game", width * 0.5f, height * 0.1f, gameTitle);



        //Load text and font information
        Paint turnText = new Paint();
        turnText.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        turnText.setTextSize(height / 32);
        turnText.setTextAlign(Paint.Align.LEFT);
        graphics2D.drawText("Turn Number: " + turnNumber, width * 0.01f, height * 0.05f, turnText);

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(height / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);
        if(displayfps)
            graphics2D.drawText("fps: " + fps, width * 1f, height * 0.05f, fpsPaint);

        drawPopUps(elapsedTime, graphics2D);


        //making use of the control variable defined
        if (gamePaused)
            drawPauseMenu(elapsedTime,graphics2D);
        else
            pauseButton.draw(elapsedTime, graphics2D,
                    boardLayerViewport,
                    mDefaultScreenViewport);


    }


    /*
    Adds cards objects to the array list of "cardCollection" - AB
     */
    private void addCardsToList(){
        ArrayList<CardInformation> cards = mGame.getAssetManager().getCards();

        for(int i = 0; i < cards.size(); i++){
            Card card = new Card(cardLayerViewport.x, cardLayerViewport.y, this, i);
            cardCollection.add(card);
        }
    }

    //Sets position of cards within cardCollection- AB
    private void setPositionCards(){
        //View Port is set to centre of screen - Note view port is not screen size calibrated
        for(int i = 0; i < numberOfCards; i++){
            int x = i * 200;  //Variable for distance between cards
            cardCollection.get(i).setPosition(cardLayerViewport.x-300 + x, cardLayerViewport.y);
        }
    }

    /*
    Used to display/draw cards from cardCollection - AB
    Future development - Auto take new line for new row of cards. (Showing deck of cards)
     */
//    private void displayCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
//        //Draw the cards into cardLayerViewport - AB
//        for(int i = 0; i < numberOfCards; i++){
//            cardCollection.get(i).draw(elapsedTime, graphics2D,
//                    cardLayerViewport,
//                    mDefaultScreenViewport);
//        }


    //}
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

    public void magnificationButton() {
            if (magnificationButton.isToggledOn()) {
                magnificationButton.setToggled(true);
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


    }










