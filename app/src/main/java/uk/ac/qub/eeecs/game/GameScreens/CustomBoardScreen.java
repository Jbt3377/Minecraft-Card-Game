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

public class CustomBoardScreen extends GameScreen {

    private PushButton backButton, leftBoardChange, rightBoardChange;
    private GameObject background, gameBoardDisplay;
    private LayerViewport boardLayerViewport;
    private int boardCounter = 0;
    private Paint textFont;
    private String boardText = "Spruce Game Board";


    public CustomBoardScreen(String screenName, Game game) {
        super("customBoardScreen", game);

        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

        //Loading Assets
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");
        mGame.getAssetManager().loadAndAddBitmap("CustomiseScreenBackground","img/CustomiseScreenBackground.png");
        mGame.getAssetManager().loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");

        background =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("CustomiseScreenBackground"), this);
        gameBoardDisplay = new GameObject(screenWidth/2, screenHeight/1.8f, screenWidth/1.8f, screenHeight/1.8f, getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()), this);

        leftBoardChange = new PushButton(screenWidth/7.2f, screenHeight/6.7f, screenWidth/9, screenHeight/7, "LeftArrow", this);
        rightBoardChange = new PushButton(screenWidth/1.16f, screenHeight/6.7f, screenWidth/9, screenHeight/7, "RightArrow", this);
        leftBoardChange.setPlaySounds(true);
        rightBoardChange.setPlaySounds(true);


        textFont = new Paint();
        textFont.setTextSize(mScreenHeight / 16);
        textFont.setARGB(255, 255, 255, 255);
        textFont.setTypeface(MainActivity.minecraftRegFont);
        textFont.setColor(Color.WHITE);
        textFont.setTextAlign(Paint.Align.CENTER);

        backButton = new PushButton(
                screenWidth* 0.1f,
                screenHeight* 0.9f,
                screenWidth /6.5f,
                screenHeight /10,
                "BackButton", this);
        backButton.setPlaySounds(true, true);

    }



    public void update(ElapsedTime elapsedTime) {

        leftBoardChange.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        rightBoardChange.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);
        backButton.update(elapsedTime, boardLayerViewport, mDefaultScreenViewport);

        boardSetter(boardCounter);
        gameBoardDisplay.update(elapsedTime);
        gameBoardDisplay.setBitmap(getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()));

        if (leftBoardChange.isPushTriggered()) {
            if (boardCounter <= 2 && boardCounter != 0) {
                boardCounter--;
            } else if (boardCounter == 0) {
                boardCounter = 2;
            }
        }

        if (rightBoardChange.isPushTriggered()) {
            if (boardCounter >= 0 && boardCounter < 2) {
                boardCounter++;
            } else if (boardCounter >= 2) {
                boardCounter = 0;
            }
        }


        if (backButton.isPushTriggered()){
            mGame.getScreenManager().removeScreen(this);
        }

    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        background.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);

        gameBoardDisplay.draw(elapsedTime, graphics2D, boardLayerViewport, mDefaultScreenViewport);

        leftBoardChange.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        rightBoardChange.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);
        backButton.draw(elapsedTime,graphics2D, boardLayerViewport, mDefaultScreenViewport);

        graphics2D.drawText(boardText,  mScreenWidth / 2f, mScreenHeight * 0.88f, textFont);


    }

    public void boardSetter(int boardCounter) {
        if (boardCounter == 0) {
            mGame.setGameboardBackground("SpruceGameBoard");
            boardText = "Spruce Game Board";

        } else if (boardCounter == 1){
            mGame.setGameboardBackground("StoneBrickGameBoard");
            boardText = "Stone Game Board";

        } else if (boardCounter == 2) {
            mGame.setGameboardBackground("QuartzGameBoard");
            boardText = "Quartz Game Board";

        }
    }
}

