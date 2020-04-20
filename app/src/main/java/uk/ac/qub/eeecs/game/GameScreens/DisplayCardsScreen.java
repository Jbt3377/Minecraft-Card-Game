package uk.ac.qub.eeecs.game.GameScreens;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

//Newly Added
import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;

import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardCollection;


public class DisplayCardsScreen extends GameScreen {

    // Viewports
    private LayerViewport backgroundLayerViewport;      //Background view port
    private LayerViewport cardLayerViewport;            //card layer view port

    // Background Image
    private GameObject backgroundBackground;            //Background object

    //PushButton
    private PushButton mReturnButton;                   //Return button

    private ArrayList<Card> displayedCardCollection;    //Card Collection

    private int numberOfColumns;                        //Number of columns for display
    private int numberOfRows;                           //Number of rows for display

    private int sizeOfRows;                             //Pixels of row

    private float distanceBetweenColumns;               //Distance Between Cards - PX
    private float distanceBetweenRows;                  //Distance Between Cards - PX

    private float touchOffsetY;                         //Offset for scrolling function

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public DisplayCardsScreen(Game game) {
        super("CardsDisplay", game);
        this.displayedCardCollection =  CardCollection.getAllCardCollection(this,game.getAssetManager().getAllCardStats());
        numberOfColumns = 4;
        numberOfRows = 2;
        touchOffsetY = 0.0f;
        setupViewPorts();
        setupBoardGameObjects();
    }

    //Setting up view ports for the DisplayCardsScreen
    private void setupViewPorts() {
        //Setup boardLayerViewport
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, screenWidth, screenHeight);
        mDefaultLayerViewport.set(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);

        cardLayerViewport = new LayerViewport(screenWidth/2,screenHeight*0.6f,screenWidth/2,screenHeight/2);
        backgroundLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
    }

    //Setting up objects for the DisplayCardsScreen
    private void setupBoardGameObjects() {

        //Calculates the rows required for being displayed
        calculateAmountOfRows();

        // Setup boardBackGround image
        backgroundBackground =  new GameObject(mScreenWidth/2, -mScreenWidth/4,
                mScreenWidth, sizeOfRows,
                getGame().getAssetManager().getBitmap("BookShelfBackground"), this);

        //Sets up the position of cards
        setCardPosition();

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

    /**
     * Update
     *
     * @param elapsedTime Elapsed time information for the frame
     */
    public void update(ElapsedTime elapsedTime) {
        processTouchEvents();
        scrollLimiter();
        movingBackground();

        mReturnButton.update(elapsedTime);
        if (mReturnButton.isPushTriggered()){
            mGame.getScreenManager().removeScreen(this);
        }
    }

    //Moves Background in an panorama effect
    private void movingBackground(){
        float panoramaOffset = cardLayerViewport.y;
        backgroundLayerViewport.y = panoramaOffset * 0.3f;
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

            //Sets the offset from the touch and card layer
            if (t.type == TouchEvent.TOUCH_DOWN) {
                touchOffsetY = cardLayerViewport.y - t.y;
            }
            //Calculation for moving the cards with the finger
            if(t.type == TouchEvent.TOUCH_DRAGGED){
                cardLayerViewport.y = t.y + touchOffsetY;
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

        //Draw the back button
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);

    }

    //Method allows future development and flexibility
    private void calculateAmountOfRows(){
        //Between Cards
        distanceBetweenRows = mDefaultLayerViewport.getHeight()*    0.6f;
        distanceBetweenColumns = mDefaultLayerViewport.getWidth()*  0.25f;

        //Variables for the display of cards
        int cardCollectionTrueSize = displayedCardCollection.size() + 1;
        numberOfRows = (cardCollectionTrueSize / numberOfColumns); //Needs to be calculated

        if((cardCollectionTrueSize % numberOfColumns)>0){
            numberOfRows++; //if there is a reminder, add 1 to row size to full display
        }
        sizeOfRows = (int)distanceBetweenRows * (numberOfRows-1); //Calculate the pixels size - -1 due to +1 from above line of code
    }

    /**
     * Display All Cards
     *
     * Displays all cards in the screen
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    private void displayAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < displayedCardCollection.size(); i++){
            displayedCardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardLayerViewport,
                    mDefaultScreenViewport);
        }
    }

    /**
     * SetPositionCards
     *
     * Sets up the positions of the cards collection
     */
    private void setCardPosition() {
        float card_start_x = mDefaultLayerViewport.getWidth() * 0.12f ; //Starting Coordinates X
        float card_start_y = mDefaultLayerViewport.getHeight() * 0.8f; //Starting Coordinates Y

        int count = 0; //used to keep count of cards increment
        for(int i = 0; i < numberOfRows; i++){
            float y = i * distanceBetweenRows;

            for(int j = 0; j < numberOfColumns; j++){
                float x = j * distanceBetweenColumns;
                if(count<displayedCardCollection.size()){
                    displayedCardCollection.get(count).setPosition(card_start_x + x, card_start_y - y);
                }
                count++;
            }
        }
    }

    /**
     * ScrollLimiter
     *
     * These are the bounds in which card layer viewport (holding all the cards) will kept.
     * Keeps the display of cards from leaving bounds
     */
    public void scrollLimiter(){

        //Top Side Limiter
        if(cardLayerViewport.y>=distanceBetweenRows){
            cardLayerViewport.y=distanceBetweenRows;
        }
        //Bottom Side Limiter
        else if(cardLayerViewport.y<=distanceBetweenRows-sizeOfRows + (mScreenHeight*0.4f)){
            cardLayerViewport.y=distanceBetweenRows-sizeOfRows + (mScreenHeight*0.4f);
        }
    }

    public LayerViewport getCardLayerViewport() {
        return cardLayerViewport;
    }

    public LayerViewport getBackgroundLayerViewport() {
        return backgroundLayerViewport;
    }


}