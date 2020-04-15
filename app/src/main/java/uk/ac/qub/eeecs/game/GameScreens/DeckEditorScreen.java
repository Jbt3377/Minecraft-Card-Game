package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;

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


public class DeckEditorScreen extends GameScreen {

    //Define a viewport for the cards
    private LayerViewport cardsLayerViewport;
    private ScreenViewport screenViewport;


    private ArrayList<Card>  displayedCardCollection = new ArrayList<>();

    //touchOffsetX for - calculating offset for moving cards
    private float touchOffsetX = 0.0f;

    //Variables for the display of cards
    private int numberOfColumns = 5; //Sets the amount of columns to display - Is calculated
    private int numberOfRows = 2; //Set the amount of rows to display

    //Distance Between Cards - PX
    private int distanceBetweenColumns = 420;
    private int distanceBetweenRows = 580;




    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DeckEditorScreen(String screenName, Game game) {
        super("DeckEditor", game);
        setupViewPorts();
        setupBoardGameObjects();
    }


    private void setupViewPorts() {
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());

        //Setup boardLayerViewport
        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();
        cardsLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
        screenViewport = new ScreenViewport(0,0,(int) screenWidth,(int)screenHeight);
    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        setPositionCards();


        displayedCardCollection = getCardCollection();
        for (int i = 0; i < displayedCardCollection.size(); i++) {
            displayedCardCollection.get(i).setHeight(displayedCardCollection.get(i).getHeight() * 1.5f);
            displayedCardCollection.get(i).setWidth(displayedCardCollection.get(i).getWidth() * 1.5f);
        }

    }



        @Override
    public void update(ElapsedTime elapsedTime) {
            processTouchEvents();


    }

    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        drawAllCards(elapsedTime, graphics2D);

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
        int count = 0;
        for(int j = 0; j < numberOfColumns; j++){
            //Calculation for y_cor to be placed in game
            int x = j * distanceBetweenColumns;

            for(int i = 0; i < numberOfRows; i++){
                //Calculation for x_cor to be placed in game
                int y = i * distanceBetweenRows;

                if(count<getCardCollection().size()){
                    getCardCollection().get(count).setPosition((cardsLayerViewport.x/3) + x, (cardsLayerViewport.y) - y);
                }
                count++;
            }
        }
    }

    private void drawAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < getCardCollection().size(); i++){
            displayedCardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardsLayerViewport,
                    screenViewport);
        }
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
                cardsLayerViewport.x = -t.x + touchOffsetX;
            }

            //Resets off set after lifting finger
            if (t.type == TouchEvent.TOUCH_UP) {
                touchOffsetX = 0.0f;
            }

            //private ArrayList<Card> cardCollection = new ArrayList<>();
            //Sets the off set from the touch and card layer
            if (t.type == TouchEvent.TOUCH_DOWN) {
                touchOffsetX = cardsLayerViewport.x + t.x;
            }

            if (t.type == TouchEvent.TOUCH_SINGLE_TAP){

                for (Card x : getCardCollection()) {
                    if (x.getBound().contains(t.x, t.y)) {

                    }
                }
            }
        }
    }



}