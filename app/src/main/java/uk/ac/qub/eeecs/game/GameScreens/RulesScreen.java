package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;



public class RulesScreen extends GameScreen {
//Variables
//to get back to the main screen
    private PushButton mBackButton;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public RulesScreen(String screenName, Game game) {
        super("Rules", game);

        // Create and position a small back button in the lower-right hand corner
        // of the screen. Also, enable click sounds on press/release interactions.
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "Redbutton", "Red-Button-Active", this);
        mBackButton.setPlaySounds(true, true);
    }



    public void update(ElapsedTime elapsedTime) {

        // Update the back button. If triggered then return to the demo menu.
        mBackButton.update(elapsedTime);
        if (mBackButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);
    }

    /**
     * Internal paint variable, defined externally to reduce object creation costs
     */
    private Paint textPaint = new Paint();

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.GREEN);

        // Determine font properties - created so a total of twenty
        // lines of text (0.05) could fit into the screen, aligned
        // along the x axis and drawn in black.

        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.BLACK);

        // Draw text displaying the name of this screen and relevant info

        graphics2D.drawText("Screen: [" +
                        this.getName() + "]", mDefaultScreenViewport.centerX(),
                mDefaultScreenViewport.top + 2.0f * textSize, textPaint);


        // Update the paint instance to draw the larger text values.
        // Sized so two lines of text can be drawn in a dark grey colour.

        textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.5f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.DKGRAY);

        // Draw the integer value managed by this game screen. Aside: the
        // intention is the value will be displayed in the middle of the
        // screen. The text will be centered along the x-axis but needs to be
        // offset on y axis to appeared center. In order to do this correctly,
        // the paint.getTextBounds() method should be used to determine the
        // size of the text and then this drawn centered.

        graphics2D.drawText(
                "Rules" ,
                mDefaultScreenViewport.centerX(),
                mDefaultScreenViewport.centerY() * 1.2f, textPaint);

        // Draw the back button
        mBackButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
