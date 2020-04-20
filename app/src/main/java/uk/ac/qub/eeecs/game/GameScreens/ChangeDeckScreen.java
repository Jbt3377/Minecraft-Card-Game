package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

public class ChangeDeckScreen extends GameScreen {

    //Game Objects Declared
    private GameObject background, selectedDeckPointer, deckButton1, deckButton2,  deckButton3, deckButton4,  deckButton5, deckButton6, customDeckButton, backButton;
    private LayerViewport boardLayerViewport;
    private Paint textFont;
    //Default Deck Options set - allows them to be changed in the future
    private String deckOption1 = "Steve's Arsenal";
    private String deckOption2 = "The Village";
    private String deckOption3 = "Bane of Herobrine";
    private String deckOption4 = "Old McDonald's Farm";
    private String deckOption5 = "Hefty Bois";
    private String deckOption6 = "The End";
    //Animation game object properties
    private GameObject[] chestAnimationStates;
    private int frames;
    //Deck Editor screen implemented - allows
    private DeckEditorScreen DeckEditor;



    public ChangeDeckScreen(Game game) {
        super("changeDeckScreen", game);

        int screenWidth = game.getScreenWidth();
        int screenHeight = game.getScreenHeight();

        //Viewports
        mDefaultScreenViewport.set( 0, 0, game.getScreenWidth(), game.getScreenHeight());
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

        //Loading Assets
        game.getAssetManager().loadAssets("txt/assets/ChangeDeckScreenAssets.JSON");

        //Game Objects
        background =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("ChangeDeckScreenBackground"), this);
        //Setup Bitmap for all use of all deckbuttons - allows for easy change later if needed
        Bitmap deckButtonBitmap = getGame().getAssetManager().getBitmap("DeckButton");
        //All Buttons created
        deckButton1 = new GameObject(screenWidth/3.28f, screenHeight/4.38f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        deckButton2 = new GameObject(screenWidth/1.535f, screenHeight/4.38f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        deckButton3 = new GameObject(screenWidth/3.28f, screenHeight/2.275f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        deckButton4 = new GameObject(screenWidth/1.535f, screenHeight/2.275f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        deckButton5 = new GameObject(screenWidth/1.535f, screenHeight/1.55f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        deckButton6 = new GameObject(screenWidth/1.535f, screenHeight*0.845f, screenWidth/5.45f, screenHeight/7, deckButtonBitmap, this);
        customDeckButton = new GameObject(screenWidth/2.7f, screenHeight*0.845f, screenWidth/11, screenHeight/6.5f, getGame().getAssetManager().getBitmap("CustomDeckButton"), this);
        backButton = new GameObject(screenWidth* 0.1f, screenHeight/11, screenWidth /6.5f, screenHeight /10, getGame().getAssetManager().getBitmap("BackButton"), this);

        //Pointer to show selected deck declared
        selectedDeckPointer = new GameObject(deckButton1.position.x + 305, deckButton1.position.y, screenWidth * 0.05f, screenHeight * 0.08f, game.getAssetManager().getBitmap("LeftArrow"), this);

        //Setting up animation states and properties
        chestAnimationStates = new GameObject[4];
        setupAnimationStates();
        frames = 0;

        //Creating a Deck Editor screen to allow it to be displayed later
        DeckEditor = new DeckEditorScreen(game);
    }


    public void update(ElapsedTime elapsedTime) {

        //Calling method to update Buttons and processing their touch events if they are "pushed" or "touched"
        updateButtons(elapsedTime);

        //Updating the frames and essentially setting the speed for a full run of the animation
        if(frames<64){
            frames++;
        } else {
            frames = 0;
        }
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Setting up fonts using paint
        textFont = new Paint();
        textFont.setTextSize(mScreenHeight / 45);
        textFont.setARGB(255, 255, 255, 255);
        textFont.setTypeface(MainActivity.minecraftRegFont);
        textFont.setColor(Color.WHITE);
        textFont.setTextAlign(Paint.Align.CENTER);

        //Drawing the background
        background.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        //Calling method to draw buttons along with the text
        drawButtonsWithText(elapsedTime, graphics2D);

        //Setting what state the animation should be in during a certain timeframe
        int states;
        if(frames<14) {
            states = 0;
        } else if (frames < 28) {
            states = 1;
        }
        else if (frames<42) {
            states = 2;
        }
        else {
            states = 3;
        }

        //Drawing the animation image of whatever state
        chestAnimationStates[states].draw(elapsedTime, graphics2D, boardLayerViewport, mDefaultScreenViewport);

    }

    //Method to draw all buttons and all text
    private void drawButtonsWithText(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        backButton.draw(elapsedTime,graphics2D);
        deckButton1.draw(elapsedTime,graphics2D);
        deckButton2.draw(elapsedTime,graphics2D);
        deckButton3.draw(elapsedTime,graphics2D);
        deckButton4.draw(elapsedTime,graphics2D);
        deckButton5.draw(elapsedTime,graphics2D);
        deckButton6.draw(elapsedTime,graphics2D);
        customDeckButton.draw(elapsedTime,graphics2D);
        selectedDeckPointer.draw(elapsedTime,graphics2D);

        //Text to be displayed is based on the deck options that can be set
        graphics2D.drawText(deckOption1,  deckButton1.position.x, deckButton1.position.y*1.04f, textFont);
        graphics2D.drawText(deckOption2,  deckButton2.position.x, deckButton2.position.y *1.04f, textFont);
        graphics2D.drawText(deckOption3,  deckButton3.position.x, deckButton3.position.y *1.03f, textFont);
        graphics2D.drawText(deckOption4,  deckButton4.position.x, deckButton4.position.y *1.03f, textFont);
        graphics2D.drawText(deckOption5,  deckButton5.position.x, deckButton5.position.y *1.02f, textFont);
        graphics2D.drawText(deckOption6,  deckButton6.position.x, deckButton6.position.y *1.01f, textFont);

    }

    //Setting a method to update Buttons and processing their touch events if they are "pushed" or "touched"
    private void updateButtons(ElapsedTime elapsedTime) {
        //Update Buttons
        backButton.update(elapsedTime);
        deckButton1.update(elapsedTime);
        deckButton2.update(elapsedTime);
        deckButton3.update(elapsedTime);
        deckButton4.update(elapsedTime);
        deckButton5.update(elapsedTime);
        deckButton6.update(elapsedTime);
        customDeckButton.update(elapsedTime);

        //Getting Touch events
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if (touchEvents.size() > 0) {

            //Listening for the user inputs
            for (TouchEvent t : touchEvents) {
                float x_cor = t.x;
                float y_cor = t.y;

                //If the back button is pushed (and released) then, removes the current screen and a sound will play
                if (t.type == TouchEvent.TOUCH_UP && backButton.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    mGame.getScreenManager().removeScreen(this);
                }

                //If a deck button is pushed, a sound will play, the selectedDeckPointer will move and the correct deck will be set and passed through to Player1
                if (t.type == TouchEvent.TOUCH_UP && deckButton1.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton1.position.x + 305;
                    selectedDeckPointer.position.y = deckButton1.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption1);
                }
                if (t.type == TouchEvent.TOUCH_UP && deckButton2.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton2.position.x + 305;
                    selectedDeckPointer.position.y = deckButton2.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption2);
                }
                if (t.type == TouchEvent.TOUCH_UP && deckButton3.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton3.position.x + 305;
                    selectedDeckPointer.position.y = deckButton3.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption3);
                }
                if (t.type == TouchEvent.TOUCH_UP && deckButton4.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton4.position.x + 305;
                    selectedDeckPointer.position.y = deckButton4.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption4);
                }
                if (t.type == TouchEvent.TOUCH_UP && deckButton5.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton5.position.x + 305;
                    selectedDeckPointer.position.y = deckButton5.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption5);
                }
                if (t.type == TouchEvent.TOUCH_UP && deckButton6.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    selectedDeckPointer.position.x = deckButton6.position.x + 305;
                    selectedDeckPointer.position.y = deckButton6.position.y;
                    mGame.getPlayer1().setmSelectedDeckName(deckOption6);
                }

                //If the custom deck button is pushed (and released), a sound will play then:
                if (t.type == TouchEvent.TOUCH_UP && customDeckButton.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    if (mGame.getmDeckManager().isCustomDeckAdded()) {
                        //If a custom deck has been set, the selectedDeckPointer will move and the correct deck will be set and passed through to Player1
                        selectedDeckPointer.position.x = customDeckButton.position.x + 205;
                        selectedDeckPointer.position.y = customDeckButton.position.y;
                        mGame.getPlayer1().setmSelectedDeckName("Custom Deck");
                    } else {
                        //Else then, the player will be shown a deck editor screen
                        mGame.getScreenManager().addScreen(DeckEditor);
                    }
                }
            }
        }
    }

    //Sets up the images for the animation states and what image is to be shown
    public void setupAnimationStates() {
        GameObject state1 = new GameObject(mScreenWidth/2.7f, mScreenHeight/6.6f, mScreenWidth/4, mScreenHeight/2, getGame().getAssetManager().getBitmap("endchest1"), this);
        this.chestAnimationStates[0] = state1;
        GameObject state2 = new GameObject(mScreenWidth/2.7f, mScreenHeight/6.4f, mScreenWidth/4, mScreenHeight/2, getGame().getAssetManager().getBitmap("endchest2"), this);
        this.chestAnimationStates[1] = state2;
        GameObject state3 = new GameObject(mScreenWidth/2.7f, mScreenHeight/6.2f, mScreenWidth/4, mScreenHeight/2, getGame().getAssetManager().getBitmap("endchest3"), this);
        this.chestAnimationStates[2] = state3;
        GameObject state4 = new GameObject(mScreenWidth/2.7f, mScreenHeight/6, mScreenWidth/4, mScreenHeight/2, getGame().getAssetManager().getBitmap("endchest4"), this);
        this.chestAnimationStates[3] = state4;
    }
    ///////////////////////////////////////
    // General Getters
    ///////////////////////////////////////
    public GameObject getDeckButton1() {
        return deckButton1;
    }
    public GameObject getDeckButton2() {
        return deckButton2;
    }
    public GameObject getDeckButton3() {
        return deckButton3;
    }
    public GameObject getDeckButton4() {
        return deckButton4;
    }
    public GameObject getDeckButton5() {
        return deckButton5;
    }
    public GameObject getDeckButton6() {
        return deckButton6;
    }
    public GameObject getCustomDeckButton() {
        return customDeckButton;
    }
}

