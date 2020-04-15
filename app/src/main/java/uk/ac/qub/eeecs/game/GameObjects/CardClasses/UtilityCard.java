package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class UtilityCard extends Card {


    //Properties
    boolean animationInProgress = false;
    int utilityFlipTimer = 45;
    boolean animationFinished = false;


    //Public constructors
    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
    }

    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats,int changeSize) {
        super(x, y, gameScreen, cardStats, changeSize);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
    }


    public void runCardAnimation(){
        if(utilityFlipTimer == 0){
            setWidth(0);
            setHeight(0);
            this.setAnimationFinished(true);
        }
        else{
            setWidth(DEFAULT_CARD_WIDTH - (scale * (FLIP_TIME - flipTimer)));
            setHeight(DEFAULT_CARD_HEIGHT - (scale * (FLIP_TIME - flipTimer)));
        }

        flipTimer--;
    }

    public void runUtilityEffect(GameBoard gameBoard){
        gameBoard.getActivePlayer().setmPlayerHealth(gameBoard.getActivePlayer().getmPlayerHealth() + 4);
    }



    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }


    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }
}
