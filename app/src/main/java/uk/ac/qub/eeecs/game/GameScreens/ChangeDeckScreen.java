package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

public class ChangeDeckScreen extends GameScreen {

    private PushButton backButton, deckButton1, deckButton2,  deckButton3, deckButton4,  deckButton5, deckButton6, customDeckButton;
    private GameObject background, selectedDeckPointer;
    private LayerViewport boardLayerViewport;
    private Paint textFont;
    private PushButton selectedDeckButton;
    private String deckOption1 = "Steve's Arsenal";
    private String deckOption2 = "The Village";
    private String deckOption3 = "Bane of Herobrine";
    private String deckOption4 = "Old McDonald's Farm";
    private String deckOption5 = "Hefty Bois";
    private String deckOption6 = "The End";


    public ChangeDeckScreen(String screenName, Game game) {
        super("changeDeckScreen", game);

        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

        //Loading Assets
        mGame.getAssetManager().loadAssets("txt/assets/ChangeDeckScreenAssets.JSON");


        background =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("ChangeDeckScreenBackground"), this);

        deckButton1 = new PushButton(screenWidth/3.28f, screenHeight/1.3f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        deckButton2 = new PushButton(screenWidth/1.535f, screenHeight/1.3f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        deckButton3 = new PushButton(screenWidth/3.28f, screenHeight/1.79f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        deckButton4 = new PushButton(screenWidth/1.535f, screenHeight/1.79f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        deckButton5 = new PushButton(screenWidth/1.535f, screenHeight/2.83f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        deckButton6 = new PushButton(screenWidth/1.535f, screenHeight/6.4f, screenWidth/5.45f, screenHeight/7, "DeckButton", this);
        customDeckButton = new PushButton(screenWidth/2.7f, screenHeight/6.4f, screenWidth/11, screenHeight/6.5f, "CustomDeckButton", this);

        selectedDeckPointer = new GameObject(deckButton1.position.x + 305, deckButton1.position.y, screenWidth * 0.05f, screenHeight * 0.08f, game.getAssetManager().getBitmap("LeftArrow"), this);

        textFont = new Paint();
        textFont.setTextSize(mScreenHeight / 45);
        textFont.setARGB(255, 255, 255, 255);
        textFont.setTypeface(MainActivity.minecraftRegFont);
        textFont.setColor(Color.WHITE);
        textFont.setTextAlign(Paint.Align.CENTER);



        backButton = new PushButton(screenWidth* 0.1f, screenHeight* 0.9f, screenWidth /6.5f, screenHeight /10, "BackButton", this);
        backButton.setPlaySounds(true);

        selectedDeckButton = deckButton1;
    }

    public void selectedDeckPointer(){
        if (selectedDeckButton == customDeckButton) {
            selectedDeckPointer.position.x = selectedDeckButton.position.x + 205;
        } else {
            selectedDeckPointer.position.x = selectedDeckButton.position.x + 305;
        }
        selectedDeckPointer.position.y = selectedDeckButton.position.y;
    }

    public void update(ElapsedTime elapsedTime) {


        backButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton1.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton2.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton3.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton4.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton5.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        deckButton6.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        customDeckButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);


        if (backButton.isPushTriggered()){
            mGame.getScreenManager().removeScreen(this);
        }
        if (deckButton1.isPushTriggered()) {
            selectedDeckButton = deckButton1;
            mGame.player1.setmSelectedDeckName(deckOption1);

        }
        if (deckButton2.isPushTriggered()) {
            selectedDeckButton = deckButton2;
            mGame.player1.setmSelectedDeckName(deckOption2);
        }
        if (deckButton3.isPushTriggered()) {
            selectedDeckButton = deckButton3;
            mGame.player1.setmSelectedDeckName(deckOption3);
        }
        if (deckButton4.isPushTriggered()) {
            selectedDeckButton = deckButton4;
            mGame.player1.setmSelectedDeckName(deckOption4);
        }
        if (deckButton5.isPushTriggered()) {
            selectedDeckButton = deckButton5;
            mGame.player1.setmSelectedDeckName(deckOption5);
        }
        if (deckButton6.isPushTriggered()) {
            selectedDeckButton = deckButton6;
            mGame.player1.setmSelectedDeckName(deckOption6);
        }
        if (customDeckButton.isPushTriggered()) {
            selectedDeckButton = customDeckButton;

            //Not Implemented
            //mGame.player1.setmSelectedDeckName("Custom Deck");
            mGame.player1.setmSelectedDeckName(deckOption1);
        }

        selectedDeckPointer();
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        background.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        backButton.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton1.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton2.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton3.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton4.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton5.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        deckButton6.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        customDeckButton.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        selectedDeckPointer.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);

        graphics2D.drawText(deckOption1,  deckButton1.position.x, deckButton1.position.y / 3.2f, textFont);
        graphics2D.drawText(deckOption2,  deckButton2.position.x, deckButton2.position.y / 3.2f, textFont);
        graphics2D.drawText(deckOption3,  deckButton3.position.x, deckButton3.position.y / 1.23f, textFont);
        graphics2D.drawText(deckOption4,  deckButton4.position.x, deckButton4.position.y / 1.23f, textFont);
        graphics2D.drawText(deckOption5,  deckButton5.position.x, deckButton5.position.y * 1.86f, textFont);
        graphics2D.drawText(deckOption6,  deckButton6.position.x, deckButton6.position.y * 5.48f, textFont);

    }


}

