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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor - Bitmap Image
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public PopUpObject(float x, float y, Bitmap bitmap, GameScreen gameScreen, int displayTime) {
        super(x, y, bitmap, gameScreen);

        this.displayTime = displayTime;

        setupTextPaint(gameScreen);
        gameScreen.popUpObjects.add(this);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor - Bitmap Image and Text
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PopUpObject(float x, float y, Bitmap bitmap, GameScreen gameScreen, int displayTime,
                       String textInput) {
        super(x, y, bitmap, gameScreen);

        this.displayTime = displayTime;
        this.textInput = textInput;

        setupTextPaint(gameScreen);
        gameScreen.popUpObjects.add(this);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Update method for PopUps - Removes them if displayTime expires
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void update(ElapsedTime elapsedTime) {
        super.update(elapsedTime);
        displayTime--;

        if (displayTime <= 0)
            mGameScreen.popUpObjects.remove(this);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

          super.draw(elapsedTime, graphics2D);
          if(textInput != null)
              graphics2D.drawText(textInput, position.x, position.y, textPaint);

    }


    /**
     * Update the card demo screen
     *
     * @param gameScreen Used for accessing gameScreen dimensions
     */

    public void setupTextPaint(GameScreen gameScreen){

        textPaint = new Paint();
        textPaint.setTypeface(MainActivity.minecraftRegFont);
        textPaint.setTextSize(gameScreen.getGame().getScreenWidth() * 0.030f);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }



}
