package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.TextAlignmentUtil;

/**
 * Prompt class drawn using overlapping images, text and buttons.
 *
 */

public class PopUpObject extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    private int displayTime;
    private String textInput;
    private Paint textPaint;
    private boolean movingPopUp;
    private int movementSpeed = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor for image popup
     * @param bitmap - popup image
     */
    public PopUpObject(float x, float y, Bitmap bitmap, GameScreen gameScreen, int displayTime) {
        super(x, y, bitmap, gameScreen);

        this.displayTime = displayTime;
        this.textInput = null;
        this.movingPopUp = false;

        setupTextPaint(gameScreen);
        gameScreen.popUpObjects.add(this);

    }


    /**
     * Constructor for image popup with overlaying text
     * @param bitmap - popup image
     * @param textInput - overlaying text
     */
    public PopUpObject(float x, float y, Bitmap bitmap, GameScreen gameScreen, int displayTime,
                       String textInput) {

        super(x, y, bitmap, gameScreen);

        this.displayTime = displayTime;
        this.textInput = textInput;
        this.movingPopUp = false;
        setupTextPaint(gameScreen);
        gameScreen.popUpObjects.add(this);

    }


    /**
     * Constructor for moving text
     * @param textInput - text to display on screen
     * @param goingUp - bool determines direction of movement
     */
    public PopUpObject(float x, float y, GameScreen gameScreen, int displayTime, String textInput,
                       int movementSpeed, boolean goingUp) {

        // Bitmap temporary - testing
        super(x, y, gameScreen.getGame().getAssetManager().getBitmap("potion_of_healing"), gameScreen);

        this.displayTime = displayTime;
        this.textInput = textInput;
        this.movingPopUp = true;

        setupTextPaint(gameScreen);

        //Depending on goingUp set move speed
        if (goingUp)
            this.movementSpeed = -movementSpeed;
        else
            this.movementSpeed = movementSpeed;

        gameScreen.popUpObjects.add(this);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void update(ElapsedTime elapsedTime) {
        super.update(elapsedTime);
        displayTime--;

        // Remove popup if display time has expired
        if (displayTime <= 0)
            mGameScreen.popUpObjects.remove(this);

        // Move popup position on y axis
        position.add(0, movementSpeed);
    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        if (!movingPopUp) {

            // Draw: Image
            super.draw(elapsedTime, graphics2D);

            if (textInput != null)
                // Draw: Text
                graphics2D.drawText(textInput, position.x, position.y, textPaint);

        }else
            // Draw: Moving Text
            graphics2D.drawText(textInput, position.x, position.y, textPaint);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method used to setup text paint
     * @param gameScreen Used for accessing gameScreen dimensions
     */
    public void setupTextPaint(GameScreen gameScreen){

        textPaint = new Paint();
        textPaint.setTypeface(MainActivity.minecraftRegFont);
        textPaint.setTextSize(gameScreen.getGame().getScreenWidth() * 0.030f);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }






}
