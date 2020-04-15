package uk.ac.qub.eeecs.game.GameScreens;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;

public class EndGameScreen extends GameScreen {

    private GameObject[] redParrotStates;
    private GameObject screenBackground, speechBubble;
    private int frameCount;
    private PushButton mReturnButton;


    public EndGameScreen(Game game, GameBoard gameBoard){
        super("EndGameScreen", game);

        this.redParrotStates = setupRedParrot();
        this.frameCount = 0;
        this.screenBackground =  new GameObject(mScreenWidth/2, mScreenHeight/2, mScreenWidth, mScreenHeight, getGame().getAssetManager().getBitmap("EndGameScreenBackground"), this);

        String speechBubbleAssetName = "";
        if(gameBoard.getPlayer2() instanceof Ai){
            if(gameBoard.getPlayer1().getmPlayerHealth()<=0){
                speechBubbleAssetName+="PlayerLose";
            }else if(gameBoard.getPlayer2().getmPlayerHealth() <=0){
                speechBubbleAssetName+="PlayerWin";
            }
        }else{
            if(gameBoard.getPlayer1().getmPlayerHealth()<=0){
                speechBubbleAssetName+="Player2Win";
            }else if(gameBoard.getPlayer2().getmPlayerHealth() <=0){
                speechBubbleAssetName+="Player1Win";
            }
        }
        this.speechBubble =  new GameObject(mScreenWidth*0.5f,mScreenHeight*0.35f, 1000, 300, getGame().getAssetManager().getBitmap(speechBubbleAssetName), this);


        this.mReturnButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.5f,
                mDefaultLayerViewport.getHeight() * 0.3f,
                100, 30,
                "BackButton", this);
        this.mReturnButton.setPlaySounds(true, true);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        mReturnButton.update(elapsedTime);
        if (mReturnButton.isPushTriggered()) {
            mGame.getScreenManager().removeScreen(this);
            mGame.getScreenManager().removeScreen("CardScreen");
        }

        if(frameCount<12) frameCount++;
        else frameCount=0;
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        screenBackground.draw(elapsedTime, graphics2D);
        speechBubble.draw(elapsedTime, graphics2D);
        mReturnButton.draw(elapsedTime, graphics2D,
                mDefaultLayerViewport, mDefaultScreenViewport);


        // Decide which parrot state should be drawn
        int parrotStateToDraw = determineParrotStateToDraw(frameCount);

        // Draw relevant animation state
        System.out.println(frameCount);
        redParrotStates[parrotStateToDraw].draw(elapsedTime, graphics2D);
    }


    /**
     * Method determines the relevant parrot state to draw
     */
    private int determineParrotStateToDraw(int frameCount){
        int parrotStateToDraw;

        if(frameCount<2) parrotStateToDraw = 0;
        else if(frameCount<4) parrotStateToDraw = 1;
        else if(frameCount<6) parrotStateToDraw = 2;
        else if(frameCount<8) parrotStateToDraw = 3;
        else if(frameCount<10) parrotStateToDraw = 4;
        else parrotStateToDraw = 5;

        return parrotStateToDraw;
    }


    /**
     * Method creates a gameObject for each state of the parrot animation, and returns an
     * array of parrot states
     */
    private GameObject[] setupRedParrot(){
        GameObject[] redParrotStates = new GameObject[6];
        for(int i=0; i<6; i++){
            String parrotAssetName = "RedParrotState" + (i+1);
            GameObject redParrotState = new GameObject(mGame.getScreenWidth()*0.7f,
                    getGame().getScreenHeight()*0.6f,
                    mGame.getAssetManager().getBitmap(parrotAssetName),
                    this);
            redParrotStates[i] = redParrotState;
        }
        return redParrotStates;
    }

}
