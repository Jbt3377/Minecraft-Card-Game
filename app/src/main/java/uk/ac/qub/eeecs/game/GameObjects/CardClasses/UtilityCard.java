package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class UtilityCard extends Card {

    int effect_intensity;

    //Public constructors
    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mBitmap = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
        this.effect_intensity = cardStats.getEffect_intensity();
    }

    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats,int changeSize) {
        super(x, y, gameScreen, cardStats, changeSize);
        this.mBitmap = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
    }


    public void runCardAnimation(){
        if(flipTimer == 0){
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
        switch(this.getCardID()) {
            case 32 :
            case 36 :
                healPlayerHP(gameBoard);
                break;
            case 33 :
            case 34 :
            case 35 :
                damageEnemyHP(gameBoard);
                break;
        }
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

    public int getEffect_intensity() {
        return effect_intensity;
    }

    public void setEffect_intensity(int effect_intensity) {
        this.effect_intensity = effect_intensity;
    }

    public void damageEnemyHP(GameBoard gameBoard) {
        gameBoard.getInactivePlayer().setmPlayerHealth(gameBoard.getInactivePlayer().getmPlayerHealth() - this.effect_intensity);
    }

    public void healPlayerHP(GameBoard gameBoard) {
        gameBoard.getActivePlayer().setmPlayerHealth(gameBoard.getActivePlayer().getmPlayerHealth() + this.effect_intensity);
    }

}
