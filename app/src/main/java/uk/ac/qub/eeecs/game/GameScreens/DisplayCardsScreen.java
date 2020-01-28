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
import uk.ac.qub.eeecs.gage.world.GameScreen;

//Newly Added
import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;


public class DisplayCardsScreen extends GameScreen {
    //Variables
    //to get back to the main screen
    private PushButton mBackButton;

    //Variables for the display of cards
    private int DisplayColumns = 4;
    private int DisplayRows = 3;

    //find remainder ("mod" % 4)

    //Distance Between Cards - PX
    private int distanceBetweenCards = 300;

    //Scrolling variables - Needs messed with
    private final float SCROLL_HEIGHT = 600.0f; // THIS NEEDS SET TO messed with to calculate for the ammount of cards
    private final float SCROLL_WIDTH = 600.0f;

    //All the cards that will be displayed
    private ArrayList<Card> cardCollection = new ArrayList<>();

    //Define a viewport for the cards
    private LayerViewport cardLayerViewport;

    private ScreenViewport screenViewport;

    private float touchOffsetX;
    private float touchOffsetY = 0.0f;

    private float mAccelerationY = 0.0f;




    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DisplayCardsScreen(Game game) {
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

        screenViewport = new ScreenViewport(0,0,(int) screenWidth,(int)screenHeight);

    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        //Adds all the cards in the game into a collection of an arraylist;
        addCardsToList();

        //Sets the position of cards
        setPositionCards();

    }


    private float t2;

    //Update requirements
    public void update(ElapsedTime elapsedTime) {

        // Get access to the input manager held by the game engine
        Input input = mGame.getInput();

        // Process any touch events occurring since the last update
        // Get any touch events that have occurred since the last update
        List<TouchEvent> touchEventList = input.getTouchEvents();

        for(TouchEvent t : touchEventList) {

            if(t.type == TouchEvent.TOUCH_DRAGGED){

                cardLayerViewport.y = t.y + touchOffsetY;

            }

            if (t.type == TouchEvent.TOUCH_UP) {
                touchOffsetY = 0.0f;
            }


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

        //Displays Cards
        displayAllCards(elapsedTime, graphics2D);

        Paint fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.RIGHT);
// Debug Draw (Keep)
//        graphics2D.drawText("cardlayerviewpoint: " + cardLayerViewport.y, mScreenWidth * 1f, mScreenHeight * 0.05f, fpsPaint);
//        graphics2D.drawText("touchOffsetY: " + touchOffsetY, mScreenWidth * 1f, mScreenHeight * 0.08f, fpsPaint);

    }


    //Converts JSON to CardInformation and adds cards to cardCollection
    private void addCardsToList(){
        ArrayList<CardInformation> cards = mGame.getAssetManager().getCards();

        for(int i = 0; i < cards.size(); i++){
            Card card = new Card(cardLayerViewport.x, cardLayerViewport.y, this, i);
            cardCollection.add(card);
        }
    }


    //Draws all the cards displaycards
    private void displayAllCards(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        //Draw the cards into cardLayerViewport - AB
        for(int i = 0; i < cardCollection.size(); i++){
            cardCollection.get(i).draw(elapsedTime, graphics2D,
                    cardLayerViewport,
                    screenViewport);
        }
    }


    //Sets the position in a grid manner = rows and colums
    private void setPositionCards(){
        int count = 0;
        //View Port is set to centre of screen - Note view port is not screen size calibrated
        for(int i = 0; i < DisplayRows; i++){
            int y = i * distanceBetweenCards;

            for(int j = 0; j < DisplayColumns; j++){
                int x = j * distanceBetweenCards;
                cardCollection.get(count).setPosition((cardLayerViewport.x/10) + x, (cardLayerViewport.y/2) - y);
                count++;
            }
        }
    }

}
