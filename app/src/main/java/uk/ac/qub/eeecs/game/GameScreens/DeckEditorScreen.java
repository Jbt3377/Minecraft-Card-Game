package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
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

    private AudioManager audioManager = mGame.getAudioManager();


           // audioManager.play(getGameScreen().getGame().getAssetManager().getSound("ShipStart")

    private final int MAX_DECK_SIZE = 20;                   //Max Card in deck
    private final int MAX_COUNT_COUNT = 3;                  //Max card count

    private int currentDeckSize = 0;                        //Deck Variables



    //Distance Between Cards - PX
    private float distanceBetweenColumns;
    private float distanceBetweenRows;


    private Paint deckPaint, UIpaint, cardCounterPaint, cardCounterMaxPaint, cardCounterBackground;

    private GameObject background;                          //Background image


    private ArrayList<Card> cardCollection;                 //All cards in the game
    private ArrayList<Integer>  deck;                       //Current cards in deck
    private ArrayList<CardStats> statsCollection;           //All Card Stats in the game

    private PushButton saveDeckButton, mReturnButton, leftButton, rightButton, clearDeckButton;

    //Define a viewport for the cards
    private LayerViewport cardsLayerViewport;
    private LayerViewport screenViewport;


    private ArrayList<GameObject> RemoveCardButtons;
    private int[] cardCount;



    //Variables for the display of cards - test
    private int numberOfColumns = 5; //Sets the amount of columns to display - Is calculated
    private int numberOfRows = 2; //Set the amount of rows to display
    private int numberOfPage;

    private int numDisplay = 0;

    //Sound audioAddToDeck = new Sound(mGame.getAudioManager().getSoundPool().)



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
        cardCount = new int[cardCollection.size()];

        numberOfPage = cardCollection.size()/10 + 1; //Calculation for developing number of pages displayed
        loadAssets();
        setupViewPorts();
        setupBoardGameObjects();
        setupPaint();

    }




    private void loadAssets(){
        //Loading Assets
        mGame.getAssetManager().loadAndAddBitmap("LeftArrow","img/GameButtons/LeftArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("RightArrow","img/GameButtons/RightArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("RemoveCardButton","img/RemoveCardButton.png");
        mGame.getAssetManager().loadAndAddBitmap("Clear_DeckButton","img/Clear_DeckButton.png");
        mGame.getAssetManager().loadAndAddBitmap("Save_Deck","img/Save_Deck.png");
        mGame.getAssetManager().loadAndAddBitmap("Save_Deck_Highlight","img/Save_Deck_Highlight.png");
        mGame.getAssetManager().loadAndAddBitmap("DeckEditorBackground", "img/DeckEditorBackground.png");

        //Sound //zoom-in
        mGame.getAssetManager().loadAndAddSound("Card_Drop","sound/assetSoundEffects/deckEditorSoundEffects/Card_Drop.mp3");
        mGame.getAssetManager().loadAndAddSound("Deck_Save","sound/assetSoundEffects/deckEditorSoundEffects/Deck_Save.mp3");
        mGame.getAssetManager().loadAndAddSound("Card_Select","sound/assetSoundEffects/deckEditorSoundEffects/Card_Select.mp3");
        mGame.getAssetManager().loadAndAddSound("Deck_Drop","sound/assetSoundEffects/deckEditorSoundEffects/Deck_Drop.mp3");
    }

    private void setupPaint(){
        //deck
        deckPaint = new Paint();
        deckPaint.setTextSize(mScreenHeight / 48);
        deckPaint.setARGB(255,255,255,255);
        deckPaint.setTextAlign(Paint.Align.RIGHT);
        deckPaint.setTypeface(MainActivity.minecraftRegFont);

        UIpaint = new Paint();
        UIpaint.setTextSize(mScreenHeight / 22);
        UIpaint.setARGB(255,255,255,255);
        UIpaint.setTextAlign(Paint.Align.RIGHT);
        UIpaint.setTypeface(MainActivity.minecraftRegFont);

        cardCounterPaint = new Paint();
        cardCounterPaint.setTextSize(mScreenHeight / 10);
        cardCounterPaint.setARGB(255,255,255,255);

        cardCounterMaxPaint = new Paint();
        cardCounterMaxPaint.setTextSize(mScreenHeight / 10);
        cardCounterMaxPaint.setARGB(255,255,0,0);

        cardCounterBackground = new Paint();
        cardCounterBackground.setTextSize(mScreenHeight / 10);
        cardCounterBackground.setARGB(255,0,0,0);


//        cardCounterMaxPaint = new Paint();
//        cardCounterPaint.setTextSize(mScreenHeight / 20);
//        cardCounterPaint.setARGB(255,255,10,10);

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

        background =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("DeckEditorBackground"), this);

        setPositionCards();


        //Set Size of Cards
        for (int i = 0; i < cardCollection.size(); i++) {
            cardCollection.get(i).setHeight(mScreenHeight * 0.35f);
            cardCollection.get(i).setWidth(mScreenWidth * 0.12f);
        }

        for(int i = 0; i < cardCollection.size(); i++){

            GameObject temp = new GameObject(
                    mScreenWidth * 0.06f + (cardCollection.get(i).getCurrentXPosition()),
                    mScreenHeight * 0.19f  + (cardCollection.get(i).getCurrentYPosition()),
                    mScreenWidth * 0.03f,
                    mScreenHeight * 0.06f,
                    getGame().getAssetManager().getBitmap("RemoveCardButton"),
                    this);
            RemoveCardButtons.add(temp);
        }

        saveDeckButton = new PushButton(
                mScreenWidth * 0.12f,
                mScreenHeight * 0.95f,
                mScreenWidth /8,
                mScreenHeight /10,
                "Save_Deck", this);



        mReturnButton = new PushButton(
                mScreenWidth * 0.92f,
                mScreenHeight * 0.08f,
                mScreenWidth /8,
                mScreenHeight /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);

        leftButton = new PushButton(
                mScreenWidth * 0.02f,
                mScreenHeight * 0.5f,
                mScreenWidth * 0.03f,
                mScreenHeight * 0.1f,
                "LeftArrow", this);
        leftButton.setPlaySounds(true, true);

        rightButton = new PushButton(
                mScreenWidth * 0.78f,
                mScreenHeight * 0.5f,
                mScreenWidth * 0.03f,
                mScreenHeight * 0.1f,
                "RightArrow", this);
        rightButton.setPlaySounds(true, true);

        clearDeckButton = new PushButton(
                mScreenWidth * 0.3f,
                mScreenHeight * 0.95f,
                mScreenWidth /8,
                mScreenHeight /10,
                "Clear_DeckButton", this);
    }



        @Override
    public void update(ElapsedTime elapsedTime) {

            processTouchEvents(elapsedTime);

            saveDeck(elapsedTime);

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


            if(deck.size() > 0){
                clearDeckButton.update(elapsedTime);
                if (clearDeckButton.isPushTriggered()) {
                    clearDeck();
                }
            }



    }



    private void removeCardFromDeck(int cardIndex){
        for (int j = 0; j < deck.size(); j++) {
            if (cardCollection.get(cardIndex).getCardID() == deck.get(j)) {
                deck.remove(j);
                currentDeckSize--;
                cardCount[cardIndex]--;
                audioManager.play(mGame.getAssetManager().getSound("Card_Drop"));
                break;
            }
        }
    }

    private void addCardToDeck(int cardIndex){

        if(deck.size() < MAX_DECK_SIZE){ //Keeps inside deck range - Cap

            if(cardIndex<cardCollection.size()) {//Prevent any out of bounds crash
                currentDeckSize++;
                cardCount[cardIndex]++;
                deck.add(cardCollection.get(cardIndex).getCardID()); //add to deck
                //mGame.getAudioManager().play();
                //Add Sounds
                audioManager.play(mGame.getAssetManager().getSound("Card_Select"));

            }
        }
    }




    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        background.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport,
                mDefaultScreenViewport);

        //Limits the screen to 10 instances at once
        for(int i = numDisplay; i < numDisplay+10;i++){
            //Prevents out of bounds access for cards that don't exist (not exactly 10 on screen)
            if(i < cardCollection.size()) {
                //Draw in layer order
                drawAllCards(elapsedTime, graphics2D, i);
                drawRemoveCardButton(elapsedTime, graphics2D, i);
                drawCardCount(elapsedTime, graphics2D, i);
            }
        }
        drawDeckList(elapsedTime, graphics2D);

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

        if(deck.size() > 0){
            clearDeckButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }
    }


    private void drawCardCount(ElapsedTime elapsedTime, IGraphics2D graphics2D, int i){
        //Only Draws if there is a code added to deck
        if (cardCount[i] == 3){
            graphics2D.drawText( "█",
                    mScreenWidth * -0.065f + (cardCollection.get(i).getCurrentXPosition()),
                    mScreenHeight * -0.12f + (cardCollection.get(i).getCurrentYPosition()),
                    cardCounterBackground);
            graphics2D.drawText(cardCount[i] + "",
                    mScreenWidth * -0.06f + (cardCollection.get(i).getCurrentXPosition()),
                    mScreenHeight * -0.12f + (cardCollection.get(i).getCurrentYPosition()),
                    cardCounterMaxPaint);

        }else if(cardCount[i] > 0) {
            graphics2D.drawText( "█",
                    mScreenWidth * -0.065f + (cardCollection.get(i).getCurrentXPosition()),
                    mScreenHeight * -0.12f + (cardCollection.get(i).getCurrentYPosition()),
                    cardCounterBackground);
            graphics2D.drawText(cardCount[i] + "",
                    mScreenWidth * -0.06f + (cardCollection.get(i).getCurrentXPosition()),
                    mScreenHeight * -0.12f + (cardCollection.get(i).getCurrentYPosition()),
                    cardCounterPaint);


        }
    }


//        for (int l = numDisplay; l < numDisplay+10;l++) {
//            cardCount.get(l).draw(elapsedTime, graphics2D);
//        }




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

    private void drawAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D, int i){
        //Draw the cards into cardLayerViewport - AB
        cardCollection.get(i).draw(elapsedTime, graphics2D);
    }

    private void drawRemoveCardButton(ElapsedTime elapsedTime, IGraphics2D graphics2D, int i){

        for(int j = 0; j < deck.size(); j++) {
            if (cardCollection.get(i).getCardID() == deck.get(j)) {
                RemoveCardButtons.get(i).draw(elapsedTime, graphics2D);
            }
        }
    }

    private void drawDeckList(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        graphics2D.drawText("Deck: " + currentDeckSize+ "/" + MAX_DECK_SIZE, mScreenWidth, mScreenHeight * 0.05f, UIpaint);

        for(int i = 0; i < deck.size(); i++){
            String nameOfCard = "";
            for(int k = 0; k < statsCollection.size(); k++){
                if(statsCollection.get(k).getId()==deck.get(i)){
                    nameOfCard = statsCollection.get(k).getName();
                }
            }
            graphics2D.drawText( nameOfCard , mScreenWidth, ((mScreenHeight / 29) * i) + (mScreenHeight * 0.1f), deckPaint);
        }
    }


    //Listens and takes in user inputs
    private void processTouchEvents(ElapsedTime elapsedTime) {
        // Get access to the input manager held by the game engine
        Input input = mGame.getInput();

        // Process any touch events occurring since the last update
        // Get any touch events that have occurred since the last update
        List<TouchEvent> touchEventList = input.getTouchEvents();

        if (touchEventList.size() > 0) {

            //Listening for the user inputs
            for (TouchEvent t : touchEventList) {
                float x_cor = t.x;
                float y_cor = t.y;

                if (t.type == TouchEvent.TOUCH_SINGLE_TAP) {
                    for (int i = numDisplay; i < numDisplay + 10; i++) {
                        if (i < cardCollection.size()) {
                            if (cardCollection.get(i).getBoundingBox().contains(t.x, t.y)) {
                                //Check to add to deck
                                if (checkCardCountLimit(i)) {
                                    addCardToDeck(i);
                                }
                            }

                            if (RemoveCardButtons.get(i).getBound().contains(x_cor, y_cor)) {
                                removeCardFromDeck(i);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkCardCountLimit(int x){
        if(cardCount[x] < MAX_COUNT_COUNT){ //Card Limit
            return true;
        }
        //play error sound
        return false;
    }

    private void clearDeck(){
        deck = new ArrayList<>(); //Clear all ID's of deck
        cardCount = new int[cardCollection.size()]; //Clear counters
        currentDeckSize = 0; //Reset deck size
        audioManager.play(mGame.getAssetManager().getSound("Deck_Drop"));
    }

    private void saveDeck(ElapsedTime elapsedTime) {
        saveDeckButton.update(elapsedTime);
        if (saveDeckButton.isPushTriggered()) {
            if (true) {
                //saveDeck - convert to stack
                mGame.getmDeckManager().addDeck(deck);
                audioManager.play(mGame.getAssetManager().getSound("Deck_Save"));
                mGame.getmDeckManager().setupCustomDeck();
            }
        }
    }

}