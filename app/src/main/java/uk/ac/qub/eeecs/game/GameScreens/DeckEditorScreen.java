package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;


public class DeckEditorScreen extends GameScreen {

    private final int MAX_DECK_SIZE = 30;                   //Max Card in deck
    private final int MAX_TYPE_OF_CARD = 3;                 //Max of type

    private int currentDeckSize = 0;                        //Deck Variables



    //Distance Between Cards - PX
    private float distanceBetweenColumns;
    private float distanceBetweenRows;

//    //Distance Between Cards - PX
//    private float distanceBetweenColumns = mGame.getScreenHeight()*0.1f;
//    private float distanceBetweenRows = mGame.getScreenWidth()*0.1f;


    private Paint deckPaint, UIpaint;

    private GameObject background;                          //Background image


    private ArrayList<Card> cardCollection;                 //All cards in the game
    private ArrayList<Integer>  deck;                       //Current cards in deck
    private ArrayList<CardStats> statsCollection;           //All Card Stats in the game

    private PushButton saveDeckButton, mReturnButton, leftButton, rightButton;

    //Define a viewport for the cards
    private LayerViewport cardsLayerViewport;
    private LayerViewport screenViewport;


    private ArrayList<PushButton> RemoveCardButtons;


    //Variables for the display of cards - test
    private int numberOfColumns = 5; //Sets the amount of columns to display - Is calculated
    private int numberOfRows = 2; //Set the amount of rows to display
    private int numberOfPage;

    private int numDisplay = 0;



    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DeckEditorScreen(String screenName, Game game) {
        super("DeckEditor", game);

        //All cards in the game
        cardCollection = getCardCollection();
        //Current cards in deck
        deck = new ArrayList<>();
        statsCollection = mGame.getAssetManager().getAllCardStats();
        RemoveCardButtons = new ArrayList<>();

        numberOfPage = cardCollection.size()/10 + 1;
        loadAssets();
        setupViewPorts();
        setupBoardGameObjects();
        setupPaint();

        loadAssets();




    }
    private void loadAssets(){
        //Loading Assets
        mGame.getAssetManager().loadAndAddBitmap("LeftArrow","img/GameButtons/LeftArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("RightArrow","img/GameButtons/RightArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("RemoveCardButton","img/RemoveCardButton.png");
    }

    private void setupPaint(){
        //deck
        deckPaint = new Paint();
        deckPaint.setTextSize(mScreenHeight / 30);

        UIpaint = new Paint();
        UIpaint.setTextSize(mScreenHeight / 20);



    }
    private void setupViewPorts() {
        //Setup boardLayerViewport
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, screenWidth, screenHeight);
        mDefaultLayerViewport.set(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);

        screenViewport = new LayerViewport(0,0,screenWidth, screenHeight);
        //cardsLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth * 0.5f,screenHeight * 0.5f);
        //Distance Between Cards - PX




    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        setPositionCards();


        //Set Size of Cards
        for (int i = 0; i < cardCollection.size(); i++) {
            cardCollection.get(i).setHeight(mDefaultLayerViewport.getHeight() * 0.35f);
            cardCollection.get(i).setWidth(mDefaultLayerViewport.getWidth() * 0.12f);
        }

        for(int i = 0; i < cardCollection.size(); i++){
            int test = 1;
            if((i % 2) == 0){
                test = -1;
            }

            PushButton temp = new PushButton(
                    mDefaultLayerViewport.getWidth() * 0.06f + (cardCollection.get(i).getCurrentXPosition()),
                    mDefaultLayerViewport.getHeight() * 0.19f  + (cardCollection.get(i).getCurrentYPosition()),
                    mDefaultLayerViewport.getWidth() * 0.03f,
                    mDefaultLayerViewport.getHeight() * 0.06f,
                    "RemoveCardButton", this);
            RemoveCardButtons.add(temp);
        }

        saveDeckButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.1f,
                mDefaultLayerViewport.getHeight() * 0.95f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "ExitButton", this);
        saveDeckButton.setPlaySounds(true, true);


        //Back button setup
        mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.92f,
                mDefaultLayerViewport.getHeight() * 0.08f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);

        leftButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.04f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.03f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                "LeftArrow", this);
        leftButton.setPlaySounds(true, true);

        rightButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.76f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.03f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                "RightArrow", this);
        rightButton.setPlaySounds(true, true);


    }



        @Override
    public void update(ElapsedTime elapsedTime) {
            processTouchEvents();

            saveDeckButton.update(elapsedTime);

            saveDeck();

            mReturnButton.update(elapsedTime);
            if (mReturnButton.isPushTriggered()){
                mGame.getScreenManager().removeScreen(this);
            }

            if (numDisplay > 9) {
                leftButton.update(elapsedTime);
                if (leftButton.isPushTriggered()) {
                    numDisplay -= 10;
                }
            }

            if (numDisplay + 10 < cardCollection.size()) {
                rightButton.update(elapsedTime);
                if (rightButton.isPushTriggered()) {
                    numDisplay += 10;
                }
            }

            for(int k = numDisplay; k < numDisplay+10;k++){
                if(k < cardCollection.size()){

                       //the deck is cuting in half when pressing the delete button

                            RemoveCardButtons.get(k).update(elapsedTime);

                }
            }

            for (int l = numDisplay; l < numDisplay+10;l++) {
                if(RemoveCardButtons.get(l).isPushTriggered()){

                    for(int j = 0; j < deck.size(); j++) {
                        if (cardCollection.get(l).getCardID() == deck.get(j)) {
                            deck.remove(j);
                            currentDeckSize--;
                            break;
                        }
                    }
                    int x = 0;
                }
            }
    }

    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {




//        for (int i = numDisplay; i < numDisplay + 10; i++) {
//            if (i < cardCollection.size()) {
//                cardCollection.get(i).draw(elapsedTime, graphics2D);
//
//                if(false){
//                    //card is in deck - highlight
//                    //                graphics2D.drawBitmap(getGame().getAssetManager().getBitmap("Cow"),null, new Rect(
////                                (int) (cardCollection.get(i).getBound().getLeft()),
////                                (int) (cardCollection.get(i).getBound().getBottom()),
////                                (int) (cardCollection.get(i).getBound().getRight()),
////                                (int) (cardCollection.get(i).getBound().getTop())),
////                        deckPaint);
//                }
//
//
//            }
//        }

        // Draw the back button
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);

        if (numDisplay > 9) {
            leftButton.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }
        if (numDisplay + 10 < cardCollection.size()) {
            rightButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }

        if(deck.size() >= 20){
            saveDeckButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }



        drawDeckList(elapsedTime, graphics2D);

        drawAllCards(elapsedTime, graphics2D);

        drawRemoveCardButton(elapsedTime, graphics2D);
    }

//    private void setPositionCards() {
//        int count = 0;
//        for(int i = 0; i < numberOfRows; i++){
//            //Calculation for y_cor to be placed in game
//            int y = i * distanceBetweenRows;
//
//            for(int j = 0; j < numberOfColumns; j++){
//                //Calculation for x_cor to be placed in game
//                int x = j * distanceBetweenColumns;
//
//                if(count<getCardCollection().size()){
//                    getCardCollection().get(count).setPosition((cardsLayerViewport.x/3) + x, (cardsLayerViewport.y) - y);
//                }
//                count++;
//            }
//        }
//    }

    private void setPositionCards() {
        //Starting Point
        float card_start_x = mDefaultLayerViewport.getWidth() * 0.12f ; //* 1f;
        float card_start_y = mDefaultLayerViewport.getHeight() * 0.3f; // * 1f;


        //Between Cards
        distanceBetweenRows = mDefaultLayerViewport.getHeight()*    0.43f;
        distanceBetweenColumns = mDefaultLayerViewport.getWidth()*  0.14f;

        int count = 0;
        for(int k = 0; k < numberOfPage; k++) {

            for (int j = 0; j < numberOfColumns; j++) {
                //Calculation for y_cor to be placed in game
                float x = j * distanceBetweenColumns;

                for (int i = 0; i < numberOfRows; i++) {
                    //Calculation for x_cor to be placed in game
                    float y = i * distanceBetweenRows;

                    if (count < cardCollection.size()) {
                        cardCollection.get(count).setPosition(card_start_x + x,  card_start_y + y);
                    }
                    count++;
                }
            }
        }
    }

    private void drawAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = numDisplay; i < numDisplay+10;i++){
            if(i < cardCollection.size()){
                cardCollection.get(i).draw(elapsedTime, graphics2D);
            }
        }
    }

    private void drawRemoveCardButton(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        for(int i = numDisplay; i < numDisplay+10;i++){
            if(i < cardCollection.size()){
                for(int j = 0; j < deck.size(); j++)
                {
                    if (cardCollection.get(i).getCardID() == deck.get(j)) {
                        RemoveCardButtons.get(i).draw(elapsedTime, graphics2D);
                    }
                }
            }
        }
    }

    private void drawDeckList(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        graphics2D.drawText("Deck: " + currentDeckSize+ "/" + MAX_DECK_SIZE, mScreenWidth * 0.8f, mScreenHeight * 0.04f, UIpaint);

        //for(int i = deck.size(); i > 0; i--){
        for(int i = 0; i < deck.size(); i++){


           // statsCollection.get(i)
                graphics2D.drawText( deck.get(i).toString(), mScreenWidth * 0.8f, ((mScreenHeight / 30) * i) + (mScreenHeight * 0.08f), deckPaint);

        }
    }





    //Listens and takes in user inputs
    private void processTouchEvents(){
        // Get access to the input manager held by the game engine
        Input input = mGame.getInput();

        // Process any touch events occurring since the last update
        // Get any touch events that have occurred since the last update
        List<TouchEvent> touchEventList = input.getTouchEvents();

        //if (touchEvents.size() > 0) {

        //Listening for the user inputs
        for(TouchEvent t : touchEventList) {

//            float x_cor = t.x;
//            float y_cor = mDefaultLayerViewport.getHeight() - t.y;
//            //Calculation for moving the cards with the finger
//            if(t.type == TouchEvent.TOUCH_DRAGGED){
//                cardsLayerViewport.x = -t.x + touchOffsetX;
//            }
//
//            //Resets off set after lifting finger
//            if (t.type == TouchEvent.TOUCH_UP) {
//                touchOffsetX = 0.0f;
//            }
//
//            //private ArrayList<Card> cardCollection = new ArrayList<>();
//            //Sets the off set from the touch and card layer
//            if (t.type == TouchEvent.TOUCH_DOWN) {
//                touchOffsetX = cardsLayerViewport.x + t.x;
//            }

            if (t.type == TouchEvent.TOUCH_SINGLE_TAP){
                for(int i = numDisplay; i < numDisplay+10;i++){
                    if(i < cardCollection.size()){
                        if (cardCollection.get(i).getBoundingBox().contains(t.x, t.y)){
                            addCardToDeck(i);
                        }
                    }
                }
            }
        }
    }

    private void addCardToDeck(int cardNumber){

        if(deck.size() < MAX_DECK_SIZE){ //Keeps inside deck range

                if(cardNumber<cardCollection.size()) {//Prevent any out of bounds crash
                    currentDeckSize++;
                    deck.add(cardCollection.get(cardNumber).getCardID()); //add to deck
                }
        }
    }

//    private void removeCardFromDeck(int cardNumber){
//
//
//                deck.remove(cardNumber);
//                currentDeckSize--;
//
//
//    }





//    private void addCardToDeck(){
//
//        int cardCopies = 0;
//        for (int i = 0; i < deck.size(); i++) {
//            if (deck.get(i).getCardID() == cardNo) {
//                cardCopies++;
//            }
//        }
//    }

    private void saveDeck() {
        if (saveDeckButton.isPushTriggered()) {
            //Check if deck can be saved (30 cards+)
            if (true) {
                //saveDeck - convert to stack
                mGame.getmDeckManager().addDeck(deck);

            }
        }
    }

}