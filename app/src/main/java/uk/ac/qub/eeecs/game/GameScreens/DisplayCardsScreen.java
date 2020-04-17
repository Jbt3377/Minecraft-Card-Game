package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

//Newly Added
import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;


public class DisplayCardsScreen extends GameScreen {
    //Variables
    //to get back to the main screen
    private PushButton mReturnButton;

    //Variables for the display of cards
    private int numberOfColumns = 4; //Set the amount of columns to display
    private int numberOfRows; //Sets the amount of rows to display - Is calculated


    //Distance Between Cards - PX
    private float distanceBetweenColumns;
    private float distanceBetweenRows;

    // Viewports
    private LayerViewport backgroundLayerViewport, cardLayerViewport;

    // Background Image
    private GameObject backgroundBackground;

    private int sizeOfRows;

    //For calculates off set
    private float touchOffsetY = 0.0f;

    private ArrayList<Card> displayedCardCollection;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DisplayCardsScreen(String screenName, Game game) {
        super("CardsDisplay", game);

        displayedCardCollection = getCardCollection();


        setupViewPorts();
        setupBoardGameObjects();




    }


    //Setting up view ports for the gamescreen
    private void setupViewPorts() {
        //Setup boardLayerViewport
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, screenWidth, screenHeight);
        mDefaultLayerViewport.set(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);




        cardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
        backgroundLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        //Calculates the rows required for being displayed
        calculateAmountOfRows();

        // Setup boardBackGround image - AB
        backgroundBackground =  new GameObject(mScreenWidth/2, -mScreenWidth/4, mScreenWidth, sizeOfRows, getGame().getAssetManager().getBitmap("BookShelfBackground"), this);

        //Sets the position of cards
        setPositionCards();

        //Back button setup
        mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.92f,
                mDefaultLayerViewport.getHeight() * 0.08f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);



        //Set Size of Cards
        for (int i = 0; i < displayedCardCollection.size(); i++) {
            displayedCardCollection.get(i).setHeight(mScreenHeight * 0.55f);
            displayedCardCollection.get(i).setWidth(mScreenWidth * 0.22f);
        }


    }

    //Update requirements
    public void update(ElapsedTime elapsedTime) {

        //Updates Buttons
        //runTimeAllButtons(elapsedTime);

        processTouchEvents();

        scrollLimiter();

        movingBackground();


        mReturnButton.update(elapsedTime);
        if (mReturnButton.isPushTriggered()){
            mGame.getScreenManager().removeScreen(this);
        }
    }


    private void movingBackground(){
        float offset = cardLayerViewport.y;
        //the total number of pixels it needs to move
        backgroundLayerViewport.y = offset * 0.3f;
    }

    //Listens and takes in user inputs
    private void processTouchEvents(){
        // Get access to the input manager held by the game engine
        Input input = mGame.getInput();

        // Process any touch events occurring since the last update
        // Get any touch events that have occurred since the last update
        List<TouchEvent> touchEventList = input.getTouchEvents();

        //Listening for the user inputs
        for(TouchEvent t : touchEventList) {
            //Calculation for moving the cards with the finger
            if(t.type == TouchEvent.TOUCH_DRAGGED){
                cardLayerViewport.y = t.y + touchOffsetY;
            }

            //private ArrayList<Card> cardCollection = new ArrayList<>();
            //Sets the off set from the touch and card layer
            if (t.type == TouchEvent.TOUCH_DOWN) {
                touchOffsetY = cardLayerViewport.y - t.y;
            }
        }
    }

    /**
     * Draw the screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        backgroundBackground.draw(elapsedTime, graphics2D,
                backgroundLayerViewport,
                mDefaultScreenViewport);

        //Displays Cards
        displayAllCards(elapsedTime, graphics2D);


        // Draw the back button
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);

    }





    //Method allows future development and flexibility
    private void calculateAmountOfRows(){

        //Between Cards
        distanceBetweenRows = mDefaultLayerViewport.getHeight()*    0.6f;
        distanceBetweenColumns = mDefaultLayerViewport.getWidth()*  0.25f;


        //Variables for the display of cards
        int cardCollectionTrueSize = getCardCollection().size() + 1;
        numberOfRows = (cardCollectionTrueSize / numberOfColumns); //Needs to be calculated

        if((cardCollectionTrueSize % numberOfColumns)>0){
            numberOfRows++; //if there is a reminder, add 1 to row size to full display
        }
        sizeOfRows = (int)distanceBetweenRows * (numberOfRows-1); //Calculate the pixels size - -1 due to +1 from above line of code
    }

    //Draws all the cards displaycards
    private void displayAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < getCardCollection().size(); i++){
            displayedCardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardLayerViewport,
                    mDefaultScreenViewport);
        }
    }


    private void setPositionCards() {

        float card_start_x = mDefaultLayerViewport.getWidth() * 0.12f ; //* 1f;
        float card_start_y = mDefaultLayerViewport.getHeight() * 0.8f; // * 1f;


        int count = 0;
        for(int i = 0; i < numberOfRows; i++){
            //Calculation for y_cor to be placed in game
            float y = i * distanceBetweenRows;

            for(int j = 0; j < numberOfColumns; j++){
                //Calculation for x_cor to be placed in game
                float x = j * distanceBetweenColumns;

                if(count<getCardCollection().size()){
                    getCardCollection().get(count).setPosition(card_start_x + x, card_start_y - y);
                }
                count++;
            }
        }
    }


    //Keeps the display of cards from leaving the screen's bounds
    private void scrollLimiter(){

        //Keeping the cardLayerViewport (All the cards) displayed
        if(cardLayerViewport.y<=distanceBetweenRows-sizeOfRows){
            cardLayerViewport.y=distanceBetweenRows-sizeOfRows;
        }
        if
        (cardLayerViewport.y>=distanceBetweenRows){
           cardLayerViewport.y=distanceBetweenRows;
        }
    }
}