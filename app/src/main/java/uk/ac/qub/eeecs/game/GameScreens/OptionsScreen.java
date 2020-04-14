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


public class OptionsScreen extends GameScreen {

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


    public OptionsScreen(String screenName, Game game) {
        super("OptionsScreen", game);

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        boardLayerViewport = new LayerViewport(mScreenWidth/2,mScreenHeight/2,mScreenWidth/2,mScreenHeight/2);
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");
        mGame.getAssetManager().loadAndAddBitmap("OptionsScreenBackground","img/GameScreen Backgrounds/OptionsScreenBackground.png");
        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");


        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("OptionsScreenBackground"), this);

        mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.85f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);

        createPaints();

        humanAvatar = new Sprite(mScreenWidth/2 *0.75f, mScreenHeight/3, game.getAssetManager().getBitmap("human_avatar"), this);
        aiAvatar = new Sprite(mScreenWidth/2, mScreenHeight/3, game.getAssetManager().getBitmap("ai_avatar"), this);

        fpsToggle = new ToggleButton(mScreenWidth*0.45f, mScreenHeight*0.1f, mScreenWidth * 0.20f, mScreenHeight * 0.2f,
                "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);

        volumeButton = new PushButton(mScreenWidth / 1.38f, mScreenHeight* 0.5500f,mScreenWidth* 0.13f, mScreenHeight* 0.18f,
                "VolumeButton",  this);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {
        playBackgroundMusic();
        fps = (int) mGame.getAverageFramesPerSecond();

        mReturnButton.update(elapsedTime);
        volumeButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
        if (mReturnButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);

        Input touchInputs = mGame.getInput();
        List<TouchEvent> input = touchInputs.getTouchEvents();
        if(volumeButton.isPushTriggered()){

            if(volumecounter == 0){
                mGame.getAudioManager().setSfxVolume(0.33f);
                mGame.getAudioManager().setMusicVolume(0.33f);
                volumecounter++;
            }else if(volumecounter == 1){

                mGame.getAudioManager().setSfxVolume(0.67f);
                mGame.getAudioManager().setMusicVolume(0.67f);
                volumecounter++;
            }else if(volumecounter == 2) {

                mGame.getAudioManager().setSfxVolume(1);
                mGame.getAudioManager().setMusicVolume(1);
                volumecounter++;
            } else if (volumecounter == 3) {
                mGame.getAudioManager().setSfxVolume(0);
                mGame.getAudioManager().setMusicVolume(0);
                volumecounter = 0;
            }
        }
        fpsToggle.update(elapsedTime,boardLayerViewport,mDefaultScreenViewport);
        mGame.setDisplayFps(fpsToggle.isToggledOn());

        for (TouchEvent t : input) {
            float x_cor = t.x;
            float y_cor = t.y;

            if (t.type == TouchEvent.TOUCH_DOWN){

                if(humanAvatar.getBound().contains(x_cor, y_cor)){
                    mGame.setPlayer2Human(true);
                }else if(aiAvatar.getBound().contains(x_cor, y_cor)){
                    mGame.setPlayer2Human(false);
                }

            }
        }

    }

    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        graphics2D.drawText("Options Menu", mScreenWidth * 0.25f, mScreenHeight * 0.1f, titlePaint);

        if(mGame.isPlayer2Human()){
            textPaintHuman.setColor(Color.GREEN);
            textPaintAi.setColor(Color.WHITE);
        }else{
            textPaintHuman.setColor(Color.WHITE);
            textPaintAi.setColor(Color.GREEN);
        }

        graphics2D.drawText("Play Against:", mScreenWidth/8, mScreenHeight/3, textPaintSettings);
        humanAvatar.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Human", mScreenWidth/2 *0.75f, mScreenHeight/2 + 40f, textPaintHuman);
        aiAvatar.draw(elapsedTime, graphics2D);
        graphics2D.drawText("AI", mScreenWidth/2, mScreenHeight/2 + 40f, textPaintAi);

        graphics2D.drawText("Who goes first:", mScreenWidth/8, mScreenHeight*0.7f, textPaintSettings);

        graphics2D.drawText("FPS Counter:", mScreenWidth/8, mScreenHeight*0.9f, textPaintSettings);
        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);

        graphics2D.drawText("Volume: " + volumecounter, (int) (mScreenWidth / 1.5), mScreenHeight/3, textPaintSettings);
        volumeButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);


        if(mGame.isDisplayFps())
            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.9f, mScreenHeight * 0.05f, fpsPaint);

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