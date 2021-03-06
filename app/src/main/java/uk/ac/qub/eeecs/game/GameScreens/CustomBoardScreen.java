package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class CustomBoardScreen extends GameScreen {

    private GameObject background, gameBoardDisplay,backButton, leftBoardChange, rightBoardChange;;

    public int getBoardCounter() {
        return boardCounter;
    }

    private int boardCounter = 0;
    private Paint textFont;

    public String getBoardText() {
        return boardText;
    }

    private String boardText = "Spruce Game Board";

    public GameObject getBackButton() {
        return backButton;
    }
    public GameObject getLeftBoardChange() {
        return leftBoardChange;
    }
    public GameObject getRightBoardChange() {
        return rightBoardChange;
    }

    public CustomBoardScreen(Game game) {
        super("customBoardScreen", game);

        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();

        mDefaultScreenViewport.set( 0, 0, screenWidth, screenHeight);
        mDefaultLayerViewport.set(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);

        //Loading Assets
        mGame.getAssetManager().loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");

        //Setting up Game Objects
        background =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("CustomiseScreenBackground"), this);
        gameBoardDisplay = new GameObject(mScreenWidth/2, mScreenHeight/1.8f, mScreenWidth/1.8f, mScreenHeight/1.8f, getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()), this);
        leftBoardChange = new GameObject(mScreenWidth/7.2f, mScreenHeight/1.175f, mScreenWidth/9, mScreenHeight/7, getGame().getAssetManager().getBitmap("LeftArrow"), this);
        rightBoardChange = new GameObject(mScreenWidth/1.16f, mScreenHeight/1.175f, mScreenWidth/9, mScreenHeight/7, getGame().getAssetManager().getBitmap("RightArrow"), this);
        backButton = new GameObject(mScreenWidth* 0.1f, mScreenHeight/11, mScreenWidth /6.5f, mScreenHeight /10, getGame().getAssetManager().getBitmap("BackButton"), this);


        //Setting up Paint
        textFont = new Paint();


    }

    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        //Updating Objects
        leftBoardChange.update(elapsedTime);
        rightBoardChange.update(elapsedTime);
        backButton.update(elapsedTime);

        boardSetter(boardCounter);
        gameBoardDisplay.update(elapsedTime);
        gameBoardDisplay.setBitmap(getGame().getAssetManager().getBitmap(mGame.getGameboardBackground()));

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            for (TouchEvent t : touchEvents) {
                float x_cor = t.x;
                float y_cor = t.y;

                //If Buttons are Pushed Events
                if (t.type == TouchEvent.TOUCH_UP && leftBoardChange.getBound().contains(x_cor, y_cor)){
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    if (boardCounter <= 2 && boardCounter != 0) {
                        boardCounter--;
                    } else if (boardCounter == 0) {
                        boardCounter = 2;
                    }
                }

                if (t.type == TouchEvent.TOUCH_UP && rightBoardChange.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    if (boardCounter >= 0 && boardCounter < 2) {
                        boardCounter++;
                    } else if (boardCounter >= 2) {
                        boardCounter = 0;
                    }
                }

                if (t.type == TouchEvent.TOUCH_UP && backButton.getBound().contains(x_cor, y_cor)) {
                    mGame.getAudioManager().play(mGame.getAssetManager().getSound("ButtonDefaultPush"));
                    mGame.getScreenManager().removeScreen(this);

                }
            }
        }

    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        //Drawing Game Objects
        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        background.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport,
                mDefaultScreenViewport);

        gameBoardDisplay.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        leftBoardChange.draw(elapsedTime,graphics2D);
        rightBoardChange.draw(elapsedTime,graphics2D);
        backButton.draw(elapsedTime,graphics2D);

        textFont.setTextSize(mGame.getScreenHeight() / 16);
        textFont.setARGB(255, 255, 255, 255);
        textFont.setTypeface(MainActivity.minecraftRegFont);
        textFont.setColor(Color.WHITE);
        textFont.setTextAlign(Paint.Align.CENTER);

        graphics2D.drawText(boardText,  mScreenWidth / 2f, mScreenHeight * 0.88f, textFont);


    }

    public void boardSetter(int boardCounter) {
        //Sets the shown gameboard that would then be used in the MainGameScreen
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

