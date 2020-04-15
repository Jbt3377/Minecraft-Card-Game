package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class EquipCard extends Card {


    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
    }

    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats, int changeSize) {
        super(x, y, gameScreen, cardStats, changeSize);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
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


    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }

}
