package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;

/**
 * Card class that can be drawn using a number of overlapping images.
 *
 * Note: See the course documentation for extension/refactoring stories
 * for this class.
 *
 * @version 1.0
 */
public class Card extends Sprite implements Draggable {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    // Define the default card width and height
    //Changed the default width and height. The original values were 180 and 260 respectively - MMC
    private static final int DEFAULT_CARD_WIDTH = 180;
    private static final int DEFAULT_CARD_HEIGHT = 260;
    private static final int TEXT_MAX_LINE_LENGTH = 9;

    //Bitmap related properties
    protected Bitmap mCardBase;
    private Bitmap mCardReverse;
    private Bitmap mCardPortrait;

    //Card stat related properties
    private String cardName;
    private int cardID;
    private String cardDescription;
    private int manaCost;
    private Paint cardDescTextPaint;

    //Touch event related properties
    private boolean selected;
    private boolean cardFaceUp;
    private final int FLIP_TIME = 15;
    private int flipTimer;
    private float scale;



    //Properties to remember the cards original position if the card is being dragged
    private float x_original;
    private float y_original;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public Card(float x, float y, GameScreen gameScreen, CardStats cardStats) {
        super(x, y, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null, gameScreen);

        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        this.manaCost = cardStats.getManacost();
        this.cardID = cardStats.getId();
        this.cardName = cardStats.getName();
        this.cardDescription = cardStats.getDescText();

        this.cardFaceUp = true;
        this.cardDescTextPaint = setupDescTextPaint(assetManager);
        this.scale = (DEFAULT_CARD_WIDTH / FLIP_TIME) * 2;

        // Set the common card reverse image
        mCardReverse = assetManager.getBitmap("CardBackgroundReverse");

        // Fetch the corresponding bitmap with the card's asset name
        mCardPortrait = assetManager.getBitmap(cardName);
    }

    public Card(float x, float y, GameScreen gameScreen, CardStats cardStats, int scaleSize) {
        super(x, y, DEFAULT_CARD_WIDTH * scaleSize, DEFAULT_CARD_HEIGHT * scaleSize, null, gameScreen);

        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        this.manaCost = cardStats.getManacost();
        this.cardID = cardStats.getId();
        this.cardName = cardStats.getName();
        this.cardDescription = cardStats.getDescText();

        this.cardDescTextPaint = setupDescTextPaint(assetManager);

        // Set the common card reverse image
        mCardReverse = assetManager.getBitmap("CardBackgroundReverse");

        // Fetch the corresponding bitmap with the card's asset name
        mCardPortrait = assetManager.getBitmap(cardName);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Method will setup and return a paint object to be used for the Card Description Text.
     */
    private Paint setupDescTextPaint(AssetManager assetManager){

        cardDescTextPaint = new Paint();
        cardDescTextPaint.setTextSize(this.getBound().getWidth()/12);
        cardDescTextPaint.setARGB(255, 255, 255, 255);
        cardDescTextPaint.setTypeface(assetManager.getFont("MinecraftRegFont"));
        return cardDescTextPaint;
    }

    /**
     * Draw the game platform
     *
     * @param elapsedTime    Elapsed time information
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {
        flipAnimation();
        if(cardFaceUp) {
            // Draw the portrait
            //drawBitmap(mCardPortrait, mPortraitOffset, mPortraitScale,
            //      graphics2D, layerViewport, screenViewport);

            // Draw the card base background
            mBitmap = mCardBase;
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }
        else{
            mBitmap = mCardReverse;
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }
    }

    private BoundingBox bound = new BoundingBox();

    //Card card = new Card("hiya");

    public void flipAnimation(){
        //If no animation
        if(flipTimer == 0){
            setWidth(DEFAULT_CARD_WIDTH);
            return;
        }
        //First half of animation
        else if(flipTimer > FLIP_TIME/2){
            setWidth(DEFAULT_CARD_WIDTH - (scale * (FLIP_TIME - flipTimer)));
        }
        //Middle of animation, flip card over
        else if(flipTimer == FLIP_TIME/2){
            cardFaceUp = !cardFaceUp;
        }

        //Second half of the animation
        else if(flipTimer > 0 && flipTimer < FLIP_TIME/2){
            setWidth(scale * (FLIP_TIME/2 - flipTimer));
        }
        flipTimer--;

        cardDescTextPaint.setTextScaleX(getWidth() / DEFAULT_CARD_WIDTH);
    }
    public void cardMoveXAnimation(float desiredXLoc, float desiredYLoc){
        //Phase 1 - Fastest Movement
        if(this.position.x > desiredXLoc){
            float positionDifference = this.position.x - desiredXLoc;
            if(positionDifference > (positionDifference * 0.10)){
                setNewPosition(this.position.x - 20, this.position.y);
            }else{
                setNewPosition(this.position.x - 1, this.position.y);
            }
        } if(this.position.x < desiredXLoc){
            float positionDifference = desiredXLoc - this.position.x;
            if(positionDifference > (positionDifference * 0.10)){
                setNewPosition(this.position.x + 20, this.position.y);
            }else{
                setNewPosition(this.position.x + 1, this.position.y);
            }
        }
    }

    public void cardMoveYAnimation(float desiredXLoc, float desiredYLoc){
        //Phase 1 - Fastest Movement
        if(this.position.y > desiredYLoc){
            float positionDifference = this.position.y - desiredYLoc;
            if(positionDifference > (positionDifference * 0.10)){
                setNewPosition(this.position.x, this.position.y - 20);
            }else{
                setNewPosition(this.position.x, this.position.y - 1);
            }
        } if(this.position.y < desiredYLoc){
            float positionDifference = desiredYLoc - this.position.y;
            if(positionDifference > (positionDifference * 0.10)){
                setNewPosition(this.position.x, this.position.y + 20);
            }else{
                setNewPosition(this.position.x, this.position.y + 1);
            }
        }
    }

    public boolean readyToTurnToMob(float desiredXLoc, float desiredYLoc){
        boolean result = false;

        if(this.mBound.contains(desiredXLoc,desiredYLoc)){
            result = true;
        }
        return result;
    }

    
    ///////////////////
    //Interface Methods
    ///////////////////


    @Override
    public void setNewPosition(float newXPosition, float newYPosition) {
        setPosition(newXPosition, newYPosition);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return mBound;
    }

    @Override
    public boolean getHasBeenSelected() {
        return selected;
    }

    @Override
    public void setHasBeenSelected(boolean hasBeenTouched) {
        selected = hasBeenTouched;
    }

    @Override
    public float getCurrentXPosition() {
        return position.x;
    }

    @Override
    public float getCurrentYPosition() {
        return position.y;
    }

    @Override
    public float getOriginalXPos() {
        return x_original;
    }

    @Override
    public float getOriginalYPos() {
        return y_original;
    }

    @Override
    public void setOriginalXPos(float x_original) {
        this.x_original = x_original;
    }

    @Override
    public void setOriginalYPos(float y_original) {
        this.y_original = y_original;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Bitmap getmCardPortrait() {
        return mCardPortrait;
    }

    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getManaCost() {
        return manaCost;
    }
    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public String getCardDescription() {
        return cardDescription;
    }
    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }


}
