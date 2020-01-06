package uk.ac.qub.eeecs.game.MinecraftCardGame;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
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

    //Define the background image GameObject
    private GameObject boardBackground;

    //Define pushButtons for the game
    private PushButton endTurnButton;

    //RulesScreen Buttons
    private PushButton RulesScreenButton;
    private RulesScreen Rules;

    //private pig :)
    private GameObject pig;


    //Defined a number for the number of cards
    private int numberOfCards = 4;

    //Defined an arraylist for the collection of cards
    private ArrayList<Card> cardCollection = new ArrayList<>();

    private LayerViewport cardLayerViewport;

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

        mGame.getAssetManager().loadCard("txt/assets/MinecraftCardGameScreenCards.JSON");


       // mRightScreen = new RulesScreen("RightScreen", game);
        setupViewPorts();

        setupBoardGameObjects();

        //Loading font
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");


        //has to be inside the Constructor to create a game screen
        Rules = new RulesScreen("Rules", game);




    }

    //getters for the buttons (using in unit test)
    public PushButton getRulesScreenButtonScreenButton() { return RulesScreenButton; }
    public PushButton endTurnButton() { return endTurnButton; }

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
        //set up a button to the Rules screen
        RulesScreenButton = new PushButton(screenWidth * 0.90f, screenHeight/5,screenWidth/10,screenHeight /10,
                "Redbutton", "Red-Button-Active", this);





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
        //card.processCardTouchEvents(touchEventList, mGame);

        for(Card c : cardCollection){
            c.processCardTouchEvents(touchEventList, mGame);
        }

        for(int i = 0; i < numberOfCards; i++){
            cardCollection.get(i).update(elapsedTime);
        }

        pig.update(elapsedTime);

        //Update the endTurnButton - MMC
        endTurnButton.update(elapsedTime, boardLayerViewport,mDefaultScreenViewport);
        //update RulesScreenButton
        RulesScreenButton.update(elapsedTime, boardLayerViewport,mDefaultScreenViewport);
        //method to call Rules button to update it
        RulesButton();



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
        RulesScreenButton.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);



        //Draw text that was loaded
        Paint textPaint = new Paint();
        textPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        textPaint.setTextSize(height / 16);
        textPaint.setTextAlign(Paint.Align.CENTER);

        graphics2D.drawText("Minecraft Card Game", width * 0.5f, height * 0.1f, textPaint);

        //dispplay pig 5 secs
        //displayPig(elapsedTime , graphics2D);


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

    }




