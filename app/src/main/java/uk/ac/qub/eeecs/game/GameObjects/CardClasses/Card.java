package uk.ac.qub.eeecs.game.GameObjects.CardClasses;


import android.graphics.Paint;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;

/**
 * Card class that can be drawn using a number of overlapping images.
 * <p>
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
    protected static final int DEFAULT_CARD_WIDTH = 180;
    protected static final int DEFAULT_CARD_HEIGHT = 260;


    //Card stat related properties
    private String cardName;
    private int cardID;
    private String cardDescription;
    private int manaCost;

    //Touch event related properties
    private boolean selected;
    protected final int FLIP_TIME = 15;
    protected int flipTimer;
    protected float scale;

    //Animation related properties
    protected boolean animationInProgress;
    protected boolean animationFinished;


    //Properties to remember the cards original position if the card is being dragged
    private float x_original;
    private float y_original;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public Card(float x, float y, GameScreen gameScreen, CardStats cardStats) {
        super(x, y, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null, gameScreen);
        this.manaCost = cardStats.getManacost();
        this.cardID = cardStats.getId();
        this.cardName = cardStats.getName();
        this.cardDescription = cardStats.getDescText();
        this.scale = (DEFAULT_CARD_WIDTH / FLIP_TIME) * 2;
        this.animationInProgress = false;
        this.animationFinished = false;

    }

    public Card(float x, float y, GameScreen gameScreen, CardStats cardStats, int scaleSize) {
        super(x, y, DEFAULT_CARD_WIDTH * scaleSize, DEFAULT_CARD_HEIGHT * scaleSize, null, gameScreen);
        this.manaCost = cardStats.getManacost();
        this.cardID = cardStats.getId();
        this.cardName = cardStats.getName();
        this.cardDescription = cardStats.getDescText();
        this.scale = (DEFAULT_CARD_WIDTH / FLIP_TIME) * 2;
        this.animationInProgress = false;
        this.animationFinished = false;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    
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
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }

    public void cardMoveXAnimation(float desiredXLoc, float desiredYLoc) {
        //Phase 1 - Fastest Movement
        if (this.position.x > desiredXLoc) {
            float positionDifference = this.position.x - desiredXLoc;
            if (positionDifference > (positionDifference * 0.10)) {
                setNewPosition(this.position.x - 20, this.position.y);
            } else {
                setNewPosition(this.position.x - 1, this.position.y);
            }
        }
        if (this.position.x < desiredXLoc) {
            float positionDifference = desiredXLoc - this.position.x;
            if (positionDifference > (positionDifference * 0.10)) {
                setNewPosition(this.position.x + 20, this.position.y);
            } else {
                setNewPosition(this.position.x + 1, this.position.y);
            }
        }
    }

    public void cardMoveYAnimation(float desiredXLoc, float desiredYLoc) {
        //Phase 1 - Fastest Movement
        if (this.position.y > desiredYLoc) {
            float positionDifference = this.position.y - desiredYLoc;
            if (positionDifference > (positionDifference * 0.10)) {
                setNewPosition(this.position.x, this.position.y - 20);
            } else {
                setNewPosition(this.position.x, this.position.y - 1);
            }
        }
        if (this.position.y < desiredYLoc) {
            float positionDifference = desiredYLoc - this.position.y;
            if (positionDifference > (positionDifference * 0.10)) {
                setNewPosition(this.position.x, this.position.y + 20);
            } else {
                setNewPosition(this.position.x, this.position.y + 1);
            }
        }
    }

    public boolean readyToTurnToMob(float desiredXLoc, float desiredYLoc) {
        boolean result = false;

        if (this.mBound.contains(desiredXLoc, desiredYLoc)) {
            result = true;
        }
        return result;
    }

    public void runCardAnimation() {
        //Implementation given in each child class
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
