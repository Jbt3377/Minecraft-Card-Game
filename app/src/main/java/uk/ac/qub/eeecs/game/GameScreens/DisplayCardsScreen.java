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
    private PushButton mBackButton;




    //Variables for the display of cards
    private int numberOfColumns = 4; //Set the amount of columns to display
    private int numberOfRows; //Sets the amount of rows to display - Is calculated

    //Sprite Size Change
    private int changeSize = 2;

    //Distance Between Cards - PX
    private int distanceBetweenColumns = 420;
    private int distanceBetweenRows = 580;

    // Viewports
    private LayerViewport backgroundLayerViewport;

    // Background Image
    private GameObject backgroundBackground;

    //Define a viewport for the cards
    private LayerViewport cardLayerViewport;
    private ScreenViewport screenViewport;

    //Variables for scroll limiter method and starting cardLayerStartingCor_y
    private int cardLayerStartingCor_y = 400;
    private int sizeOfRows;
    private int card_layerDiffernce = sizeOfRows-cardLayerStartingCor_y;


    //Debugging Variables
    private float touchOffsetX;
    private float touchOffsetY = 0.0f;
    private float mAccelerationY = 0.0f;

    private ArrayList<Card> displayedCardCollection = new ArrayList<>();

    // DisplayCardsScreen
    private PushButton allCardsButton, mobCardsButton, equipCardsButton, utilityCardsButton, showUnowned;


    private int test = 0;



    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DisplayCardsScreen(String screenName, Game game) {
        super("CardsDisplay", game);
        setupViewPorts();
        setupBoardGameObjects();
    }


    //Setting up view ports for the gamescreen
    private void setupViewPorts() {
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());

        //Setup boardLayerViewport
        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();
        cardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
        backgroundLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
        screenViewport = new ScreenViewport(0,0,(int) screenWidth,(int)screenHeight);
    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {



        //Adds all the cards in the game into a collection of an arraylist;
        addCardsToCardCollection();
        addCardsToCardCollection();
        addCardsToCardCollection();
        addCardsToCardCollection();
        addCardsToCardCollection();

        //Calculates the rows required for being displayed
        calculateAmountOfRows();

        // Setup boardBackGround image - AB
        backgroundBackground =  new GameObject(mScreenWidth/2, -mScreenWidth/4, mScreenWidth, sizeOfRows, getGame().getAssetManager().getBitmap("BookShelfBackground"), this);

        //Sets the position of cards
        setPositionCards();

        //Set up buttons
        //setUpButtons();


        displayedCardCollection = getCardCollection();
        for (int i = 0; i < displayedCardCollection.size(); i++) {
            displayedCardCollection.get(i).setHeight(displayedCardCollection.get(i).getHeight() * 2);
            displayedCardCollection.get(i).setWidth(displayedCardCollection.get(i).getWidth() * 2);
        }
    }

    //Update requirements
    public void update(ElapsedTime elapsedTime) {

        //Updates Buttons
        //runTimeAllButtons(elapsedTime);

        processTouchEvents();

        scrollLimiter();

        movingBackground();


    }


    private void movingBackground(){
        float offset = cardLayerViewport.y;

        //backgorund y / card view size
        float calculate = mGame.getScreenHeight() / sizeOfRows;
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

            //Resets off set after lifting finger
            if (t.type == TouchEvent.TOUCH_UP) {
                touchOffsetY = 0.0f;
            }

            //private ArrayList<Card> cardCollection = new ArrayList<>();
            //Sets the off set from the touch and card layer
            if (t.type == TouchEvent.TOUCH_DOWN) {
                touchOffsetY = cardLayerViewport.y - t.y;
            }

            if (t.type == TouchEvent.TOUCH_SINGLE_TAP){

                for (Card x : cardCollection) {
                    if (x.getBound().contains(t.x, t.y)) {
                        test++;
                    }
                }
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

        //drawAllButtons(elapsedTime,graphics2D);

        //Displays Cards
        displayAllCards(elapsedTime, graphics2D);

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);

        graphics2D.drawText("cardlayerviewpoint: " + cardLayerViewport.y, mScreenWidth * 1f, mScreenHeight * 0.05f, fpsPaint);
        //graphics2D.drawText("touchOffsetY: " + touchOffsetY, mScreenWidth * 1f, mScreenHeight * 0.08f, fpsPaint);
        graphics2D.drawText("touchOffsetY: " + test, mScreenWidth * 1f, mScreenHeight * 0.08f, fpsPaint);

// Debug Draw (Keep)
//        graphics2D.drawText("cardlayerviewpoint: " + cardLayerViewport.y, mScreenWidth * 1f, mScreenHeight * 0.05f, fpsPaint);
//        graphics2D.drawText("touchOffsetY: " + touchOffsetY, mScreenWidth * 1f, mScreenHeight * 0.08f, fpsPaint);

    }





    //Method allows future development and flexibility
    private void calculateAmountOfRows(){
        //Variables for the display of cards
        numberOfRows = ((getCardCollection().size() + 1) / numberOfColumns); //Needs to be calculated
        sizeOfRows = distanceBetweenRows * (numberOfRows-1); //Calculate the pixels size - -1 due to +1 from above line of code
    }

    //Draws all the cards displaycards
    private void displayAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < getCardCollection().size(); i++){
            displayedCardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardLayerViewport,
                    screenViewport);
        }
    }


    private void setPositionCards() {
        int count = 0;
        for(int i = 0; i < numberOfRows; i++){
            //Calculation for y_cor to be placed in game
            int y = i * distanceBetweenRows;

            for(int j = 0; j < numberOfColumns; j++){
                //Calculation for x_cor to be placed in game
                int x = j * distanceBetweenColumns;

                if(count<getCardCollection().size()){
                    getCardCollection().get(count).setPosition((cardLayerViewport.x/3) + x, (cardLayerViewport.y) - y);
                }
                count++;
            }
        }
    }


    //Keeps the display of cards from leaving the screen's bounds
    private void scrollLimiter(){

        //Keeping the cardLayerViewport (All the cards) displayed
        if(cardLayerViewport.y<=cardLayerStartingCor_y-sizeOfRows){
            cardLayerViewport.y=cardLayerStartingCor_y-sizeOfRows;
        }
        if
        (cardLayerViewport.y>=cardLayerStartingCor_y){
           cardLayerViewport.y=cardLayerStartingCor_y;
        }

    }






//    // private PushButton allCardsButton, mobCardsButton, equipCardsButton, utilityCardsButton, showUnowned;
//    private void setUpButtons(){
//
//        float height = 0.90f; //Top side of the screen
//        float width = 0.10f; //Left side of the screen
//
//
//        float buttonSizeWidth = mDefaultLayerViewport.getWidth() * 0.075f;
//        float buttonSizeHeight = mDefaultLayerViewport.getHeight() * 0.10f;
//
//        //"All" Cards Button
//        allCardsButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * width,
//                mDefaultLayerViewport.getHeight() * height,
//                buttonSizeWidth,
//                buttonSizeHeight,
//                "BackButton", "BackButton", this);
//
//        //"Mod" Cards Button
//        mobCardsButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * (width + 0.1f),
//                mDefaultLayerViewport.getHeight() * height,
//                buttonSizeWidth,
//                buttonSizeHeight,
//                "BackButton", "BackButton", this);
//
//
//        //"Equip" Cards Button
//        equipCardsButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * (width + 0.2f),
//                mDefaultLayerViewport.getHeight() * height,
//                buttonSizeWidth,
//                buttonSizeHeight,
//                "BackButton", "BackButton", this);
//
//        //"Utility" Cards Button
//        utilityCardsButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * (width + 0.3f),
//                mDefaultLayerViewport.getHeight() * height,
//                buttonSizeWidth,
//                buttonSizeHeight,
//                "BackButton", "BackButton", this);
//
//
//        //"Back" Button
//        mBackButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * 0.95f,
//                mDefaultLayerViewport.getHeight() * 0.10f,
//                mDefaultLayerViewport.getWidth() * 0.075f,
//                mDefaultLayerViewport.getHeight() * 0.10f,
//                "BackButton", "BackButton", this);
//        mBackButton.setPlaySounds(true, true);
//
//
//
//    }
//
//    private void runTimeAllButtons(ElapsedTime elapsedTime){
//
//        //Back Button
//        mBackButton.update(elapsedTime);
//        if (mBackButton.isPushTriggered())
//            mGame.getScreenManager().removeScreen(this);
//
//        //All Cards Button
//        allCardsButton.update(elapsedTime);
//        if (allCardsButton.isPushTriggered()){
//
//        }
//
//        //Mob Cards Button
//        mobCardsButton.update(elapsedTime);
//        if (mobCardsButton.isPushTriggered()){
//
//        }
//
//        //Equip Cards Button
//        equipCardsButton.update(elapsedTime);
//        if (equipCardsButton.isPushTriggered()){
//
//        }
//
//        //Utility Cards Button
//        utilityCardsButton.update(elapsedTime);
//        if (utilityCardsButton.isPushTriggered()){
//
//        }
//    }
//
//    private void drawAllButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D){
//        mBackButton.draw(elapsedTime, graphics2D,
//                mDefaultLayerViewport, mDefaultScreenViewport);
//
//        allCardsButton.draw(elapsedTime, graphics2D,
//                mDefaultLayerViewport, mDefaultScreenViewport);
//
//        mobCardsButton.draw(elapsedTime, graphics2D,
//                mDefaultLayerViewport, mDefaultScreenViewport);
//
//        equipCardsButton.draw(elapsedTime, graphics2D,
//                mDefaultLayerViewport, mDefaultScreenViewport);
//
//        utilityCardsButton.draw(elapsedTime, graphics2D,
//                mDefaultLayerViewport, mDefaultScreenViewport);
//
//
//    }
}