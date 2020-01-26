package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;


public class OptionsScreen extends GameScreen {
    //Variables
//to get back to the main screen
    private PushButton mBackButton;
    private GameObject boardBackground;
    private LayerViewport boardLayerViewport;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public OptionsScreen(String screenName, Game game) {
        super("OptionsScreen", game);

        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

        // Create and position a small back button in the lower-right hand corner
        // of the screen. Also, enable click sounds on press/release interactions.

        //Loading font
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");
        mGame.getAssetManager().loadAndAddBitmap("RulesScreenBackground","img/RulesScreenBackground.png");
        //loading json
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");


        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "BackButton", this);
        mBackButton.setPlaySounds(true, true);


        boardBackground =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("RulesScreenBackground"), this);
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


    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);
        Paint textPaint = new Paint();


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

        graphics2D.drawText("Options", width * 0.5f, height * 0.1f, textPaint);








        // Draw the back button
        mBackButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);
    }
}