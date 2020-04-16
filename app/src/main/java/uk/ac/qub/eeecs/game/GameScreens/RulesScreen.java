package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;


public class RulesScreen extends GameScreen {

    private PushButton mReturnButton;
    private GameObject boardBackground;
    private LayerViewport boardLayerViewport;
    private Paint titlePaint, textPaintSettings, textPaintHuman, textPaintAi, fpsPaint;
    private GameObject humanAvatar, aiAvatar;
    private ToggleButton fpsToggle;
    int fps;

    private int volumecounter = 1;
    private PushButton volumeButton;
    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public RulesScreen(String screenName, Game game) {
        super("Rules", game);

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        boardLayerViewport = new LayerViewport(mScreenWidth/2,mScreenHeight/2,mScreenWidth/2,mScreenHeight/2);
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");
        mGame.getAssetManager().loadAndAddBitmap("RulesScreenBackground","img/RulesScreenBackground.png");
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");



        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("RulesScreenBackground"), this);

        mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.85f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);

        createPaints();

    }


    @Override
    public void update(ElapsedTime elapsedTime) {
        playBackgroundMusic();
        fps = (int) mGame.getAverageFramesPerSecond();

        mReturnButton.update(elapsedTime);

        if (mReturnButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);

        Input touchInputs = mGame.getInput();
        List<TouchEvent> input = touchInputs.getTouchEvents();


        }



    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        graphics2D.drawText("Rules", mScreenWidth /2, mScreenHeight * 0.1f, titlePaint);


        graphics2D.drawText("Play/Place character or mob cards on the board.", mScreenWidth/8, mScreenHeight-800, textPaintSettings);
        graphics2D.drawText("Once you attack, you cant place any more cards.", mScreenWidth/8, mScreenHeight-700 , textPaintSettings);
        graphics2D.drawText("Surplus damage inflicted to a mob will carry over and damage", mScreenWidth/8, mScreenHeight-600, textPaintSettings);
        graphics2D.drawText("the player.", mScreenWidth/8, mScreenHeight-550, textPaintSettings);
        graphics2D.drawText("Utility Cards can be used to affect you/the opponents mobs.", mScreenWidth/8, mScreenHeight-450, textPaintSettings);
        graphics2D.drawText("Equipment Cards can be used to buff the stats of one of your mobs", mScreenWidth/8, mScreenHeight-350, textPaintSettings);
        graphics2D.drawText("your mobs.", mScreenWidth/8, mScreenHeight-300, textPaintSettings);
        graphics2D.drawText("Try to get the opponents health to 0", mScreenWidth/8, mScreenHeight-200, textPaintSettings);
        graphics2D.drawText("HAVE FUN", mScreenWidth/2, mScreenHeight-50, titlePaint);


        // Draw the back button
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);
    }


    private void createPaints(){

        titlePaint = new Paint();
        titlePaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        titlePaint.setTextSize(mScreenHeight / 16);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setColor(Color.WHITE);

        textPaintSettings = new Paint();
        textPaintSettings.setTypeface(mGame.getAssetManager().getFont("MinecraftRegFont"));
        textPaintSettings.setTextSize(mScreenHeight / 24);
        textPaintSettings.setTextAlign(Paint.Align.LEFT);
        textPaintSettings.setColor(Color.WHITE);

        textPaintHuman = new Paint();
        textPaintHuman.setTypeface(mGame.getAssetManager().getFont("MinecraftRegFont"));
        textPaintHuman.setTextSize(mScreenHeight / 32);
        textPaintHuman.setTextAlign(Paint.Align.CENTER);
        textPaintHuman.setColor(Color.WHITE);

        textPaintAi = new Paint();
        textPaintAi.setTypeface(mGame.getAssetManager().getFont("MinecraftRegFont"));
        textPaintAi.setTextSize(mScreenHeight / 32);
        textPaintAi.setTextAlign(Paint.Align.CENTER);
        textPaintAi.setColor(Color.WHITE);

        fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.CENTER);
        fpsPaint.setColor(Color.WHITE);

    }
    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    //Changed string name to new background music
                    getGame().getAssetManager().getMusic("MinecraftMusic"));
    }
}