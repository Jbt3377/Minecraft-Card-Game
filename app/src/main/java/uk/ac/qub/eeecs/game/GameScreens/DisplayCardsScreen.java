package uk.ac.qub.eeecs.game.GameScreens;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

//Newly Added
import java.util.ArrayList;
import uk.ac.qub.eeecs.gage.engine.CardInformation;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;


public class DisplayCardsScreen extends GameScreen {
    //Variables
    //to get back to the main screen
    private PushButton mBackButton;

    //Variables for the display of cards
    private int DisplayRow = 4;
    private int DisplayColumns = 3;

    //Distance Between Cards - PX
    private int distanceBetweenCards = 300;

    //Defined an arraylist. Collection of cards to be displayed
    private ArrayList<Card> cardCollection = new ArrayList<>();

    //Define a viewport for the cards
    private LayerViewport cardLayerViewport;

    //Define a viewport for the collection
    private LayerViewport ScrollLayerViewport;


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
    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {

        //Adds all the cards in the game into a collection of an arraylist;
        addCardsToList();

        //Sets the position of cards
        setPositionCards();
    }






    //Update requirements
    public void update(ElapsedTime elapsedTime) {


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
                    mDefaultScreenViewport);
        }
    }


    //Sets the position in a grid manner = rows and colums
    private void setPositionCards(){
        int count = 0;
        //View Port is set to centre of screen - Note view port is not screen size calibrated
        for(int i = 0; i < DisplayColumns; i++){
            int y = i * distanceBetweenCards;

            for(int j = 0; j < DisplayRow; j++){
                int x = j * distanceBetweenCards;
                cardCollection.get(count).setNewPosition((cardLayerViewport.x/10) + x, (cardLayerViewport.y/2) + y);
                count++;
            }
        }
    }



    private void scrollFunction(){


    }
}
