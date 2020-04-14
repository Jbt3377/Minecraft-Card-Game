package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
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
    private Paint titlePaint, textPaintSettings, fpsPaint;
    private GameObject humanAvatar, aiAvatar, selectPlayer1First, selectPlayer2First;
    private ToggleButton fpsToggle;
    private GameObject[] helmetStates;
    private int frameCount;

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

        createPaints();
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        fps = (int) mGame.getAverageFramesPerSecond();

        mReturnButton.update(elapsedTime);
        if (mReturnButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);

        Input touchInputs = mGame.getInput();
        List<TouchEvent> input = touchInputs.getTouchEvents();

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

        fpsPaint = new Paint();
        fpsPaint.setTypeface(mGame.getAssetManager().getFont("MinecrafterFont"));
        fpsPaint.setTextSize(mScreenHeight / 30);
        fpsPaint.setTextAlign(Paint.Align.CENTER);
        fpsPaint.setColor(Color.WHITE);
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

}