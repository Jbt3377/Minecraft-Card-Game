package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardCollection;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;


public class DeckEditorScreen extends GameScreen {

    private final int MAX_DECK_SIZE = 20;                   //Max Card in deck
    private final int MAX_COUNT_COUNT = 3;                  //Max Card Count

    private ArrayList<Card> cardCollection;                 //All cards in the game
    private ArrayList<Integer>  deck;                       //Current cards in deck
    private ArrayList<CardStats> statsCollection;           //All Card Stats in the game
    private int[] cardCount;                                //Card Counter for card collection

    private float distanceBetweenColumns;                   //Distance Between Cards - PX
    private float distanceBetweenRows;                      //Distance Between Cards - PX

    private Paint deckPaint, interfacePaint,                //Paint for text
            cardCounterPaint, cardCounterMaxPaint,
            cardCounterBackground;

    private GameObject background;                          //Background image

    private PushButton saveDeckButton;                      //SaveButton
    private PushButton returnButton;                        //ReturnButton
    private PushButton leftButton;                          //LeftButton
    private PushButton rightButton;                         //RightButton
    private PushButton clearDeckButton;                     //ClearDeckButton

    private ArrayList<GameObject> RemoveCardButtons;        //RemoveCardButtons/Object


    //Variables for the display of cards - test
    private int numberOfColumns;                            //Number of columns for display
    private int numberOfRows;                               //Number of rows for display
    private int numberOfPage;                               //Number of pages for display

    private int numDisplay;                                 //Determines updates, draws, depending on value


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DeckEditorScreen(Game game) {
        super("DeckEditor", game);

        this.cardCollection = CardCollection.getAllCardCollection(this,game.getAssetManager().getAllCardStats());; //All cards in the game
        this.deck = new ArrayList<>();   //Current cards in deck
        this.statsCollection = mGame.getAssetManager().getAllCardStats(); //CardStats
        this.RemoveCardButtons = new ArrayList<>();
        this.cardCount = new int[cardCollection.size()];

        this.numberOfColumns = 5;
        this.numberOfRows = 2;
        this.numDisplay =0;

        this.numberOfPage = cardCollection.size()/10 + 1; //Calculation for developing number of pages displayed

        loadAssets();
        setupViewPorts();
        setupBoardGameObjects();
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

        //Sound
        mGame.getAssetManager().loadAndAddSound("Card_Drop","sound/assetSoundEffects/deckEditorSoundEffects/Card_Drop.mp3");
        mGame.getAssetManager().loadAndAddSound("Deck_Save","sound/assetSoundEffects/deckEditorSoundEffects/Deck_Save.mp3");
        mGame.getAssetManager().loadAndAddSound("Card_Select","sound/assetSoundEffects/deckEditorSoundEffects/Card_Select.mp3");
        mGame.getAssetManager().loadAndAddSound("Deck_Drop","sound/assetSoundEffects/deckEditorSoundEffects/Deck_Drop.mp3");
        mGame.getAssetManager().loadAndAddSound("Flip_Page","sound/assetSoundEffects/deckEditorSoundEffects/Flip_Page.mp3");
    }

    private void setupPaint(){
        //deck
        deckPaint = new Paint();
        deckPaint.setTextSize(mScreenHeight / 48);
        deckPaint.setARGB(255,255,255,255);
        deckPaint.setTextAlign(Paint.Align.RIGHT);
        deckPaint.setTypeface(MainActivity.minecraftRegFont);

        interfacePaint = new Paint();
        interfacePaint.setTextSize(mScreenHeight / 22);
        interfacePaint.setARGB(255,255,255,255);
        interfacePaint.setTextAlign(Paint.Align.RIGHT);
        interfacePaint.setTypeface(MainActivity.minecraftRegFont);

        cardCounterPaint = new Paint();
        cardCounterPaint.setTextSize(mScreenHeight / 10);
        cardCounterPaint.setARGB(255,255,255,255);

        cardCounterMaxPaint = new Paint();
        cardCounterMaxPaint.setTextSize(mScreenHeight / 10);
        cardCounterMaxPaint.setARGB(255,255,0,0);

        cardCounterBackground = new Paint();
        cardCounterBackground.setTextSize(mScreenHeight / 10);
        cardCounterBackground.setARGB(255,0,0,0);


    }

    private void setupViewPorts() {
        //Setup boardLayerViewport
        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, screenWidth, screenHeight);
        mDefaultLayerViewport.set(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);
    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        background =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("DeckEditorBackground"), this);
        setCardPosition();

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
        setupButtons();
    }

    public void setupButtons(){
        saveDeckButton = new PushButton(
                mScreenWidth * 0.12f,
                mScreenHeight * 0.95f,
                mScreenWidth /8,
                mScreenHeight /10,
                "Save_Deck", this);

        returnButton = new PushButton(
                mScreenWidth * 0.92f,
                mScreenHeight * 0.08f,
                mScreenWidth /8,
                mScreenHeight /10,
                "BackButton", this);
        returnButton.setPlaySounds(true, true);

        leftButton = new PushButton(
                mScreenWidth * 0.02f,
                mScreenHeight * 0.5f,
                mScreenWidth * 0.03f,
                mScreenHeight * 0.1f,
                "LeftArrow", this);

        rightButton = new PushButton(
                mScreenWidth * 0.78f,
                mScreenHeight * 0.5f,
                mScreenWidth * 0.03f,
                mScreenHeight * 0.1f,
                "RightArrow", this);

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

            if(deck.size() == MAX_DECK_SIZE){
                saveDeckButton.update(elapsedTime);
                if (saveDeckButton.isPushTriggered()) {
                    saveDeck();
                }
            }

            returnButton.update(elapsedTime);
            if (returnButton.isPushTriggered()){
                mGame.getScreenManager().removeScreen(this);
            }
            if (numDisplay > 9) {
                leftButton.update(elapsedTime);
                leftButtonTrigger();
            }
            if (numDisplay + 10 < cardCollection.size()) {
                rightButton.update(elapsedTime);
                rightButtonTrigger();
            }
            if(deck.size() > 0){
                clearDeckButton.update(elapsedTime);
                clearDeckButtonTrigger();
            }
    }

    public void leftButtonTrigger(){
        if (leftButton.isPushTriggered()) {
            numDisplay -= 10;
            mGame.getAudioManager().play(mGame.getAssetManager().getSound("Flip_Page")); //Play Sounds
        }
    }

    public void rightButtonTrigger(){
        if (rightButton.isPushTriggered()) {
            numDisplay += 10;
            mGame.getAudioManager().play(mGame.getAssetManager().getSound("Flip_Page")); //Play Sounds
        }
    }

    public void clearDeckButtonTrigger(){
        if (clearDeckButton.isPushTriggered()) {
            clearDeck();
            mGame.getAudioManager().play(mGame.getAssetManager().getSound("Deck_Drop")); //Play Sounds
        }
    }

    private void drawWithinDisplayRange(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        //Limits the screen to 10 instances at once
        for(int i = numDisplay; i < numDisplay+10;i++){
            //Prevents out of bounds access for cards that don't exist (not exactly 10 on screen)
            if(i < cardCollection.size()) {

                //DrawCards
                cardCollection.get(i).draw(elapsedTime, graphics2D);

                //Draw Remove Card Buttons
                for(int j = 0; j < deck.size(); j++) {
                    if (cardCollection.get(i).getCardID() == deck.get(j)) {
                        RemoveCardButtons.get(i).draw(elapsedTime, graphics2D);
                    }
                }

                //Draw Card Counters
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
        }
    }

    /**
     * Draw the deck editor
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        setupPaint();
        background.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport,
                mDefaultScreenViewport);

        drawWithinDisplayRange(elapsedTime,graphics2D);
        drawDeckList(elapsedTime, graphics2D);

        //Return Button
        returnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);

        //Left Button
        if (numDisplay > 9) {
            leftButton.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Right Button Button Draw
        if (numDisplay + 10 < cardCollection.size()) {
            rightButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Save Deck Button Draw
        if(deck.size() == MAX_DECK_SIZE){
            saveDeckButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Clear Deck Button Draw
        if(deck.size() > 0){
            clearDeckButton.draw(elapsedTime, graphics2D ,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }
    }

    /**
     * SetPositionCards
     *
     * Sets up the positions of the cards collection
     */
    private void setCardPosition() {
        //Starting Point
        float card_start_x = mDefaultLayerViewport.getWidth() * 0.12f ; //Starting Coordinates X
        float card_start_y = mDefaultLayerViewport.getHeight() * 0.3f; //Starting Coordinates Y

        //
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


    private void drawDeckList(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        graphics2D.drawText("Deck: " + deck.size()+ "/" + MAX_DECK_SIZE, mScreenWidth, mScreenHeight * 0.05f, interfacePaint);

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
                            if (cardCollection.get(i).getBoundingBox().contains(x_cor, y_cor)) {
                                //Check to add to deck
                                if (checkCardCountLimit(i)) {
                                    if(deck.size() < MAX_DECK_SIZE) { //Keeps inside deck range - Cap
                                        incrementCardCounter(i);
                                        addCardToDeck(cardCollection.get(i));
                                    }
                                }
                            }

                            if (RemoveCardButtons.get(i).getBound().contains(x_cor, y_cor)) {
                                for (int j = 0; j < deck.size(); j++) {
                                    if (cardCollection.get(i).getCardID() == deck.get(j)) {
                                        decrementCardCounter(i);
                                        removeCardFromDeck(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void removeCardFromDeck(int cardLocation){
        deck.remove(cardLocation);
        mGame.getAudioManager().play(mGame.getAssetManager().getSound("Card_Drop")); //Play Sounds
    }

    public void addCardToDeck(Card card){
        deck.add(card.getCardID()); //Add to card ID to deck
        mGame.getAudioManager().play(mGame.getAssetManager().getSound("Card_Select"));   //Play Sounds
    }


    public void decrementCardCounter(int index){
        cardCount[index]--;
    }
    public void incrementCardCounter(int index){
        cardCount[index]++;
    }
    public boolean checkCardCountLimit(int index){
        if(cardCount[index] < MAX_COUNT_COUNT){ //Card Limit
            return true;
        }
        return false;
    }


    public void clearDeck(){
        deck = new ArrayList<>(); //Clear all ID's of deck
        cardCount = new int[cardCollection.size()]; //Clear counters
    }

    public void saveDeck() {
        //saveDeck - convert to stack
        mGame.getmDeckManager().addDeck(deck);
        mGame.getAudioManager().play(mGame.getAssetManager().getSound("Deck_Save"));
        mGame.getmDeckManager().setupCustomDeck();

    }


    //Getters/Setters
    public int getNumDisplay(){
        return numDisplay;
    }

    public ArrayList<Integer> getDeck() {
        return deck;
    }

    public PushButton getLeftButton(){
        return this.leftButton;
    }

    public PushButton getRightButton(){
        return this.rightButton;
    }



    public ArrayList<Card> getCardCollection() {
        return cardCollection;
    }




}