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

    // Define the common card base
    private Bitmap mCardBase;

    //Define card reverse
    private Bitmap mCardReverse;

    // Define the card portrait image
    private Bitmap mCardPortrait;

    // Define the card digit images
    private Bitmap[] mCardDigits = new Bitmap[10];

    // Define the offset locations and scaling for the card portrait
    // card attack and card health values - all measured relative
    // to the centre of the object as a percentage of object size

    private Vector2 mAttackOffset = new Vector2(-0.68f, -0.84f);
    private Vector2 mAttackScale = new Vector2(0.1f, 0.1f);

    private Vector2 mHealthOffset = new Vector2(0.72f, -0.84f);
    private Vector2 mHealthScale = new Vector2(0.1f, 0.1f);

    private Vector2 mPortraitOffset = new Vector2(0.0f, 0.3f);
    private Vector2 mPortraitScale = new Vector2(0.55f, 0.55f);



    // Define the health and attack values
    private int mAttack;
    private int mHealth;


    private Paint cardDescText;


    //Define offsets for moving card
    private float touchOffsetX;
    private float touchOffsetY;
    //Define if a card has been selected
    private boolean selected;
    private boolean cardFaceUp;
    public final int FLIP_TIME = 15;
    private int flipTimer;
    private float scale;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new platform.
     *
     * @param x          Centre y location of the platform
     * @param y          Centre x location of the platform
     * @param gameScreen Gamescreen to which this platform belongs
     */
    public Card(float x, float y, GameScreen gameScreen, int index) {
        super(x, y, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null, gameScreen);

        AssetManager assetManager = gameScreen.getGame().getAssetManager();

        cardFaceUp = true;

        // Store the common card base image
        mCardBase = assetManager.getBitmap("CardBackground");

        //Store the common card reverse image
        mCardReverse = assetManager.getBitmap("CardBackgroundReverse");

        // Store the card portrait image
        mCardPortrait = assetManager.getBitmap(assetManager.getCards().get(index).getAssetName());

        // Store each of the damage/health digits
        for(int digit = 0; digit <= 9; digit++)
            mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));

        // Set default attack and health values
        mAttack = assetManager.getCards().get(index).getAttack();
        mHealth = assetManager.getCards().get(index).getDefence();


        cardDescText = new Paint();
        cardDescText.setTextSize(this.getBound().getWidth()/12);
        cardDescText.setARGB(255, 255, 255, 255);
        cardDescText.setTypeface(assetManager.getFont("MinecraftFont"));
        this.scale = (DEFAULT_CARD_WIDTH / FLIP_TIME) * 2;

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
        flipAnimation();
        if(cardFaceUp) {
            // Draw the portrait
            drawBitmap(mCardPortrait, mPortraitOffset, mPortraitScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the card base background
            mBitmap = mCardBase;
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

            // Draw the attack value
            drawBitmap(mCardDigits[mAttack], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the health value
            drawBitmap(mCardDigits[mHealth], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);
            drawTextOnCard(graphics2D);
        }
        else{
            mBitmap = mCardReverse;
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }

    }

    private BoundingBox bound = new BoundingBox();

    public String insertNewLines(String text){
        StringBuilder textBuilder = new StringBuilder("");

        // Split string into words
        String[] words = text.split(" ");

        int lineLength = 0;
        for (String word : words)
        {
            // Return null if a word exceeds the maximum limit.
            if (word.length() > TEXT_MAX_LINE_LENGTH)
            {
                return null;
            }

            // If appending this word will exceed the max length, take a new line.
            if ((lineLength + word.length()) > TEXT_MAX_LINE_LENGTH)
            {
                textBuilder.append("\n");
                lineLength = 0;
            }
            // If not appending the first word, add a space first.
            else if (lineLength > 0)
            {
                textBuilder.append(" ");
            }
            textBuilder.append(word);
            lineLength += (word.length() + 1);
        }

        return textBuilder.toString();
    }

    public void drawTextOnCard(IGraphics2D graphics2D){
        float y = position.y + (getHeight() * 1/10) ;
        float convertedY = convertYAxisToLayerView(y - (mBound.getHeight() * 5 / 20));
        String testText = insertNewLines("Example Text");
        for(String line : testText.split("\n")) {

            graphics2D.drawText(line,
                    position.x - (mBound.getWidth() * 14 / 43),
                    convertedY += (cardDescText.getTextSize() + 8),
                    cardDescText);
        }

    }


    /**
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param bitmap Bitmap to draw
     * @param offset Offset vector
     * @param scale Scaling vector
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     */
    private void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale,
        IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Calculate a game layer bound for the bitmap to be drawn
//        bound.set(position.x + mBound.halfWidth * offset.x,
//                position.y + mBound.halfHeight * offset.y,
//                mBound.halfWidth * scale.x,
//                mBound.halfHeight * scale.y);

        // Calculate the center position of the rotated offset point.
        double rotation = Math.toRadians(-this.orientation);
        float diffX = mBound.halfWidth * offset.x;
        float diffY = mBound.halfHeight * offset.y;
        float rotatedX = (float)(Math.cos(rotation) * diffX - Math.sin(rotation) * diffY + position.x);
        float rotatedY = (float)(Math.sin(rotation) * diffX + Math.cos(rotation) * diffY + position.y);

        // Calculate a game layer bound for the bitmap to be drawn
        bound.set(rotatedX, rotatedY,
                mBound.halfWidth * scale.x, mBound.halfHeight * scale.y);

        // Draw out the specified bitmap using the calculated bound.
        // The following code is based on the Sprite's draw method.
        if (GraphicsHelper.getSourceAndScreenRect(
                bound, bitmap, layerViewport, screenViewport, drawSourceRect, drawScreenRect)) {

            // Build an appropriate transformation matrix
            drawMatrix.reset();

            float scaleX = (float) drawScreenRect.width() / (float) drawSourceRect.width();
            float scaleY = (float) drawScreenRect.height() / (float) drawSourceRect.height();
            drawMatrix.postScale(scaleX, scaleY);

            drawMatrix.postRotate(orientation, scaleX * bitmap.getWidth()
                    / 2.0f, scaleY * bitmap.getHeight() / 2.0f);

            drawMatrix.postTranslate(drawScreenRect.left, drawScreenRect.top);

            // Draw the bitmap
            graphics2D.drawBitmap(bitmap, drawMatrix, null);
        }
    }
            //Card card = new Card("hiya");


    //Processes all touch events for the card
    public void processCardTouchEvents(List<TouchEvent> input, Game mGame) {
        this.ensureObjectStaysInView(mGame);

        for (TouchEvent t : input) {
            float x_cor = t.x;
            float y_cor = convertYAxisToLayerView(t.y);

            //Changes the cardFaceUp boolean if the card is single tapped - MMC
            if(t.type == TouchEvent.TOUCH_SINGLE_TAP  && mBound.contains(x_cor,y_cor)){
                if (mGame.isMagnificationToggled()) {


                } else {
                    flipTimer = FLIP_TIME;
                }
            }

            if(mBound.contains(x_cor,y_cor) && t.type == TouchEvent.TOUCH_DOWN){
                selected = true;
                touchOffsetX = x_cor - position.x;
                touchOffsetY = y_cor - position.y;
            }


            if(t.type == TouchEvent.TOUCH_DRAGGED && selected){
                setPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);

            }

            if(t.type == TouchEvent.TOUCH_UP){
                selected = false;
            }

        }
    }

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

        cardDescText.setTextScaleX(getWidth() / DEFAULT_CARD_WIDTH);
    }

    ///////////////////
    //Interface Methods
    ///////////////////


    @Override
    public void setPosition(float newXPosition, float newYPosition) {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Bitmap getmCardPortrait() {
        return mCardPortrait;
    }

    public int getmAttack() {
        return mAttack;
    }

    public int getmHealth() {
        return mHealth;
    }

}
