package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;


public class OptionsScreen extends GameScreen {

    private PushButton mReturnButton;
    private GameObject boardBackground;
    private LayerViewport boardLayerViewport;
    private Paint titlePaint, textPaintSettings, fpsPaint, Testpaint;
    private GameObject humanAvatar, aiAvatar, selectPlayer1First, selectPlayer2First;
    private ToggleButton fpsToggle;
    private boolean fpsDrawn;
    int fps;
    private GameObject[] helmetStates;
    private int frameCount;

    private int volumecounter = 1;
    private PushButton volumeButton;
    private float music1,music2,music3,music4;
    private float sfx1,sfx2,sfx3,sfx4;


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

        this.frameCount = 0;
        this.helmetStates = new GameObject[4];


        boardBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("OptionsScreenBackground"), this);

        mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.85f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() /8,
                mDefaultLayerViewport.getHeight() /10,
                "BackButton", this);
        mReturnButton.setPlaySounds(true, true);

        //Volume Button
        volumeButton = new PushButton(mScreenWidth / 1.38f, mScreenHeight* 0.5500f,mScreenWidth* 0.13f, mScreenHeight* 0.18f,
                "VolumeButton",  this);


        // Opponent Selection Objects
        humanAvatar = new Sprite(mScreenWidth/2 *0.75f, mScreenHeight/3, game.getAssetManager().getBitmap("human_avatar"), this);
        aiAvatar = new Sprite(mScreenWidth/2, mScreenHeight/3, game.getAssetManager().getBitmap("ai_avatar"), this);

        // First Player Selection Objects
        selectPlayer1First = new GameObject(mScreenWidth/2 *0.75f, mScreenHeight*0.65f, 250, 250, game.getAssetManager().getBitmap("HelmetState1"), this);
        selectPlayer2First = new GameObject(mScreenWidth/2, mScreenHeight*0.65f, 250, 250, game.getAssetManager().getBitmap("HelmetState1"), this);
        setupHelmetStates();

        // FPS Toggle Objects
        fpsToggle = new ToggleButton(mScreenWidth*0.45f, mScreenHeight*0.1f, mScreenWidth * 0.17f, mScreenHeight * 0.17f,
                "ToggleOff", "ToggleOff", "ToggleOn", "ToggleOn", this);
        if(mGame.isDisplayFps()) fpsToggle.setToggled(true);

        createPaints();



    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        playBackgroundMusic();
        fps = (int) mGame.getAverageFramesPerSecond();

        volumeButton.update(elapsedTime ,boardLayerViewport,mDefaultScreenViewport);
        mReturnButton.update(elapsedTime);
        if (mReturnButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);

        Input touchInputs = mGame.getInput();
        List<TouchEvent> input = touchInputs.getTouchEvents();
       //logic for the volume button (if)

            volumebuttontriggred();


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
                }else if(selectPlayer1First.getBound().contains(x_cor, y_cor)){
                    mGame.setPlayer1First(true);
                    updateAnimationCoOrdinates();
                }else if(selectPlayer2First.getBound().contains(x_cor, y_cor)) {
                    mGame.setPlayer1First(false);
                    updateAnimationCoOrdinates();
                }
            }
        }

        if(frameCount<16) frameCount++;
        else frameCount=0;

    }

    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        boardBackground.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        // Draw Title
        graphics2D.drawText("Options Menu", mScreenWidth * 0.25f, mScreenHeight * 0.1f, titlePaint);

        // Draw Opponent Selection
        graphics2D.drawText("Play Against:", mScreenWidth/8, mScreenHeight/3, textPaintSettings);
        humanAvatar.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Human", mScreenWidth/2 *0.75f, mScreenHeight/2 + 40f, smallTextPaint(mGame.isPlayer2Human()));
        aiAvatar.draw(elapsedTime, graphics2D);
        graphics2D.drawText("AI", mScreenWidth/2, mScreenHeight/2 + 40f, smallTextPaint(!mGame.isPlayer2Human()));

        // Draw First Player Selection
        graphics2D.drawText("Who goes first:", mScreenWidth/8, mScreenHeight*0.7f, textPaintSettings);
        selectPlayer1First.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Player 1", mScreenWidth/2 *0.75f, mScreenHeight*0.8f, smallTextPaint(mGame.isPlayer1First()));
        selectPlayer2First.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Player 2", mScreenWidth/2, mScreenHeight*0.8f, smallTextPaint(!mGame.isPlayer1First()));

        // Draw FPS Selection
        graphics2D.drawText("FPS Counter:", mScreenWidth/8, mScreenHeight*0.9f, textPaintSettings);
        fpsToggle.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);
        // Draw Volume Button info
        graphics2D.drawText("Volume: " + volumecounter, (int) (mScreenWidth / 1.5), mScreenHeight/3, textPaintSettings);
        volumeButton.draw(elapsedTime, graphics2D, boardLayerViewport,mDefaultScreenViewport);





        // Draw FPS if enabled
        if(mGame.isDisplayFps())

            graphics2D.drawText("fps: " + fps, mScreenWidth * 0.9f, mScreenHeight * 0.05f, fpsPaint);




        // Draw relevant animation state
        int helmetStateToDraw;
        if(frameCount<4) helmetStateToDraw = 0;
        else if(frameCount<8) helmetStateToDraw = 1;
        else if(frameCount<12) helmetStateToDraw = 2;
        else helmetStateToDraw = 3;

        System.out.println(frameCount);
        helmetStates[helmetStateToDraw].draw(elapsedTime, graphics2D, boardLayerViewport, mDefaultScreenViewport);

        // Draw the back button
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);
    }


    /**
     * Method sets up each helmet state as a game object and stores in an array of animation states
     */
    private void setupHelmetStates(){

        GameObject helmetState1 = new GameObject(mScreenWidth/2 *0.75f, mScreenHeight *0.35f, 250, 250, getGame().getAssetManager().getBitmap("HelmetState1"), this);
        this.helmetStates[0] = helmetState1;
        GameObject helmetState2 = new GameObject(mScreenWidth/2 *0.75f, mScreenHeight *0.35f, 250, 250, getGame().getAssetManager().getBitmap("HelmetState2"), this);
        this.helmetStates[1] = helmetState2;
        GameObject helmetState3 = new GameObject(mScreenWidth/2 *0.75f, mScreenHeight *0.35f, 250, 250, getGame().getAssetManager().getBitmap("HelmetState3"), this);
        this.helmetStates[2] = helmetState3;
        GameObject helmetState4 = new GameObject(mScreenWidth/2 *0.75f, mScreenHeight *0.35f, 250, 250, getGame().getAssetManager().getBitmap("HelmetState4"),this);
        this.helmetStates[3] = helmetState4;
    }

    /**
     * Method sets up Paint objects required for different levels of text on screen
     */
    public void createPaints(){


        titlePaint = createAPaint("Center","White","MinecrafterFont",mScreenHeight / 16);
        textPaintSettings = createAPaint("Left","White","MinecrafterFont",mScreenHeight / 24);
        fpsPaint = createAPaint("Center","White","MinecrafterFont",mScreenHeight / 30);

    }

    /**
     * Method creates a paint object for option selection text. Text is set to green if it is used
     * as the currently selected option
     */
    private Paint smallTextPaint(Boolean isGreen){

        Paint smallTextPaint = new Paint();
        smallTextPaint.setTypeface(mGame.getAssetManager().getFont("MinecraftRegFont"));
        smallTextPaint.setTextSize(mScreenHeight / 32);
        smallTextPaint.setTextAlign(Paint.Align.CENTER);
        if(isGreen)
            smallTextPaint.setColor(Color.GREEN);

        return smallTextPaint;
    }

    /**
     * Method updates the position of the animation game objects dependent on which player
     * is set to have the first turn
     */
    private void updateAnimationCoOrdinates(){

        for(GameObject helmetState: this.helmetStates){

            if(mGame.isPlayer1First())
                helmetState.setPosition(mScreenWidth/2 *0.75f, mScreenHeight *0.35f);
            else
                helmetState.setPosition(mScreenWidth/2, mScreenHeight *0.35f);
        }
    }

    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    //Changed string name to new background music
                    getGame().getAssetManager().getMusic("MinecraftMusic"));
    }



    public int getVolumecounter() {
        return volumecounter;
    }
    public void setVolumeButton(int num){

        this.volumecounter = num;
    }

    public void volumebuttontriggred(){

        if(volumeButton.isPushTriggered()){
        if(volumecounter == 0){
            mGame.getAudioManager().setSfxVolume(0.33f);
            mGame.getAudioManager().setMusicVolume(0.33f);
            mGame.getAudioManager().getSoundPool().autoPause();
            music1 = mGame.getAudioManager().getMusicVolume();
            sfx1 = mGame.getAudioManager().getSfxVolume();
            volumecounter++;
        }else if(volumecounter == 1){

            mGame.getAudioManager().setSfxVolume(0.67f);
            mGame.getAudioManager().setMusicVolume(0.67f);
            music2 = 0.67f;
            sfx2 = mGame.getAudioManager().getSfxVolume();

            volumecounter++;
        }else if(volumecounter == 2) {

            mGame.getAudioManager().setSfxVolume(1);
            mGame.getAudioManager().setMusicVolume(1);
            music3 = mGame.getAudioManager().getMusicVolume();
            sfx3 = mGame.getAudioManager().getSfxVolume();

            volumecounter++;
        } else if (volumecounter == 3) {
            mGame.getAudioManager().setSfxVolume(0);
            mGame.getAudioManager().setMusicVolume(0);
            mGame.getAudioManager().getSoundPool().autoPause();
            music4 = mGame.getAudioManager().getMusicVolume();
            sfx4 = mGame.getAudioManager().getSfxVolume();
            volumecounter = 0;
        }
        }

    }

    public PushButton getVolumeButton() {
        return volumeButton;
    }

    public ToggleButton getFpsToggleButton(){

        return fpsToggle;
    }

    public float getFps(){

        return fps;
    }

    public Paint getFpsPaint() {
        return fpsPaint;
    }

    public float getMusic1() {
        return music1;
    }
    public float getMusic2() {
        return music2;
    }
    public float getMusic3() {
        return music3;
    }
    public float getMusic4() {
        return music4;
    }

    public float getSfx1() {
        return sfx1;
    }

    public float getSfx2() {
        return sfx2;
    }

    public float getSfx3() {
        return sfx3;
    }

    public float getSfx4() {
        return sfx4;
    }

    public Paint getTitlePaint() {
        return titlePaint;
    }

    public Paint getTestPaint(){

        return Testpaint;
    }

    public float getScreenHeight(){

        return mScreenHeight;
    }

    public float getScreenWidth(){

        return mScreenWidth;
    }


    public Paint createAPaint(String Postion,String colour,String assetName,float size){

        Paint TextPaint = new Paint();
        TextPaint.setTypeface(mGame.getAssetManager().getFont(assetName));
        TextPaint.setTextSize(size);


        if (Postion.equalsIgnoreCase("Center"))
            TextPaint.setTextAlign(Paint.Align.CENTER);
        else if (Postion.equalsIgnoreCase("Right"))
            TextPaint.setTextAlign(Paint.Align.RIGHT);
        else if (Postion.equalsIgnoreCase("Left"))
            TextPaint.setTextAlign(Paint.Align.LEFT);

        else TextPaint.setTextAlign(Paint.Align.CENTER);


         if (colour.equalsIgnoreCase("Black"))
            TextPaint.setColor(Color.BLACK);
        else if (colour.equalsIgnoreCase("Blue"))
            TextPaint.setColor(Color.BLUE);
        else if (colour.equalsIgnoreCase("Red"))
            TextPaint.setColor(Color.RED);
        else if (colour.equalsIgnoreCase("Green"))
            TextPaint.setColor(Color.GREEN);
        else if (colour.equalsIgnoreCase("Yellow"))
            TextPaint.setColor(Color.YELLOW);
        else if (colour.equalsIgnoreCase("Purple"))
            TextPaint.setColor(Color.MAGENTA);
        else TextPaint.setColor(Color.WHITE);


        return TextPaint;
    }

}